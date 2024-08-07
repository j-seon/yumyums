import pymongo
import requests
import json
import numpy as np
from scipy.spatial.distance import cosine
import nltk


# 사용자가 입력한 값
# user_input = "정기구매 방식"ㄴㄴ
# OpenAI API 설정

url = "https://api.openai.com/v1/embeddings"
headers = {
    "Content-Type": "application/json",
    "Authorization": f"Bearer {api_key}"
}


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


def src_sentence(user_input):
    # 사용자가 입력한 내용에 대한 임베딩 생성
    user_embedding = embed_sentence(user_input)

    # MongoDB 연결 설정
    mongo_client = pymongo.MongoClient("mongodb://localhost:27017/")
    mongo_db = mongo_client["yumyums"]
    mongo_collection = mongo_db["non_token_question"]

    # MongoDB에서 데이터 조회
    cursor = mongo_collection.find()

    # 저장된 데이터와의 유사도를 계산하여 가장 유사한 값을 찾기
    max_similarity = -1
    most_similar_id = None
    most_similar_question = None
    
    for document in cursor:
        faq_id = document["id"]
        question = document["question"]
        embedding = document["embedding"]
        answer = document["answer"]
        # 코사인 유사도 계산
        similarity = 1 - cosine(embedding, user_embedding)

        # 가장 높은 유사도를 가진 데이터 찾기
        if similarity > max_similarity:
            max_similarity = similarity
            most_similar_id = faq_id
            most_similar_question = question
            most_similar_answer = answer
    # JSON 형태로 결과 반환
    result = {
        "most_similar_id": most_similar_id,
        "cosine_similarity": max_similarity,
        "user_question": user_input,
        "most_similar_question": most_similar_question,
        "answer":most_similar_answer
    }

    # 연결 종료
    mongo_client.close()

    return json.dumps(result, ensure_ascii=False, indent=4) 
    