import pymongo
import requests
import json
import numpy as np
from scipy.spatial.distance import cosine
import nltk


# 사용자가 입력한 값
# user_input = "정기구매 방식"ㄴ
# OpenAI API 설정
api_key = "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"
url = "https://api.openai.com/v1/embeddings"
headers = {
    "Content-Type": "application/json",
    "Authorization": f"Bearer {api_key}"
}


def update_rankings(rankings, new_entry):
    
    if(new_entry["similarity"]>=0.87):
        # 새로운 JSON 객체 추가
        rankings.append(new_entry)
        
        # 'cosine_similarity' 값 기준으로 내림차순 정렬
        rankings.sort(key=lambda x: x['similarity'], reverse=True)
        
        # 배열의 크기를 5로 제한
        # if len(rankings) > 5:
        #     rankings = rankings[:5]
    
    return rankings

# 문장을 임베딩하는 함수 정의
def embed_sentence(sentence):
    tokens = nltk.word_tokenize(sentence)
    
    data = {
        "input": sentence,
        "model": "text-embedding-ada-002"  # 최신 모델 사용
    }
    response = requests.post(url, headers=headers, data=json.dumps(data))
    embedding = response.json()["data"][0]["embedding"]
    
    return embedding


def src_sentence_list(user_input):
    # 사용자가 입력한 내용에 대한 임베딩 생성
    user_embedding = embed_sentence(user_input)

    returnList=[]
    # MongoDB 연결 설정
    mongo_client = pymongo.MongoClient("mongodb://localhost:27017/")
    mongo_db = mongo_client["yumyums"]
    mongo_collection = mongo_db["non_token_question"]

    # MongoDB에서 데이터 조회
    cursor = mongo_collection.find()
    
    for document in cursor:
        faq_id = document["id"]
        question = document["question"]
        embedding = document["embedding"]
        answer = document["answer"]
        # 코사인 유사도 계산
        similarity = 1 - cosine(embedding, user_embedding)

        
        # JSON 형태로 결과 반환
        result = {
            "faq_id": faq_id,
            "similarity": similarity,
            "question": question,
            "answer":answer
        }

        returnList = update_rankings(returnList, result)

    # 연결 종료
    mongo_client.close()

    return json.dumps(returnList, ensure_ascii=False, indent=4) 
    
