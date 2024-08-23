import mysql.connector
import pymongo
import json
import nltk
import requests
nltk.download('punkt')
nltk.download('punkt_tab')
# MySQL 연결 설정
mysql_conn = mysql.connector.connect(
    host="localhost",
    user="root",
    password="1234",
    database="yumyums"
)

# MongoDB 연결 설정
mongo_client = pymongo.MongoClient("mongodb://localhost:27017/")
mongo_db = mongo_client["yumyums"]
mongo_collection = mongo_db["non_token_question"]

# MySQL에서 데이터 읽기
cursor = mysql_conn.cursor()
cursor.execute("SELECT id, title,category,content FROM faq ")
rows = cursor.fetchall()

api_key = ""
url = "https://api.openai.com/v1/embeddings"
headers = {
    "Content-Type": "application/json",
    "Authorization": f"Bearer {api_key}"
}

# 문장을 임베딩하는 함수 정의
def embed_sentence(sentence):
    data = {
        "input": sentence,
        "model": "text-embedding-ada-002"  # 최신 모델 사용
    }
    response = requests.post(url, headers=headers, data=json.dumps(data))
    embedding = response.json()["data"][0]["embedding"]
    print()
    return embedding

# embedding 값을 JSON 형식으로 변환하여 MongoDB에 저장
for row in rows:
    faq_id = row[0]
    question = row[1]
    category = row[2]
    answer = row[3]
    embedding = embed_sentence(question)

    # MongoDB에 저장할 데이터 준비
    faq_data = {
        "id": faq_id,
        "question": question,
        "embedding": embedding,
        "answer_id": faq_id,
        "category": category,
        "answer":answer
    }
    print(answer)
    # MongoDB에 데이터 삽입
    mongo_collection.insert_one(faq_data)

# 연결 종료
cursor.close()
mysql_conn.close()
mongo_client.close()
