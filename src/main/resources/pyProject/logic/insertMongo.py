import mysql.connector
import pymongo
import json
import nltk
import requests

# MySQL 연결 설정?
mysql_conn = mysql.connector.connect(
    host="localhost",
    user="root",
    password="1234",
    database="yumyums"
)

# MongoDB 연결 설정
mongo_client = pymongo.MongoClient("mongodb://localhost:27017/")
mongo_db = mongo_client["yumyums"]
mongo_collection = mongo_db["question"]

# MySQL에서 데이터 읽기
cursor = mysql_conn.cursor()
cursor.execute("SELECT id, title FROM faq")
rows = cursor.fetchall()


url = "https://api.openai.com/v1/embeddings"
headers = {
    "Content-Type": "application/json",
    "Authorization": f"Bearer {api_key}"
}
# embedding 값을 JSON 형식으로 변환하여 MongoDB에 저장
for row in rows:
    faq_id = row[0]
    question = row[1]

    # 토큰화
    tokenized_sentences = [nltk.word_tokenize(question) ]
    print(tokenized_sentences)
    # 토큰화 값 임베딩 하기

    for token in tokens:
        data = {
            "input": token,
            "model": "text-embedding-ada-002"  # 최신 모델 사용
        }
        response = requests.post(url, headers=headers, data=json.dumps(data))
        embedding = response.json()["data"][0]["embedding"]
        embeddings.append(embedding)
    if embeddings:
        avg_embedding = np.mean(embeddings, axis=0).tolist()
    else:
        avg_embedding = []
    

    # 사용자가 입력한 내용에 대한 임베딩 생성
    data = {
        "input": tokenized_sentences,
        "model": "text-embedding-ada-002"  # 최신 모델 사용
    }
    response = requests.post(url, headers=headers, data=json.dumps(data))
    user_embedding = response.json()["data"][0]["embedding"]

    # MongoDB에 저장할 데이터 준비
    faq_data = {
        "id": faq_id,
        "question": question,
        "tokened_question": tokenized_sentences,
        "embedding": user_embedding,
        "answer_id": faq_id
    }
    
    # MongoDB에 데이터 삽입
    mongo_collection.insert_one(faq_data)

# 연결 종료
cursor.close()
mysql_conn.close()
mongo_client.close()
