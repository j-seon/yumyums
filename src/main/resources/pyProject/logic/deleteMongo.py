import pymongo

# MongoDB 연결 설정
mongo_client = pymongo.MongoClient("mongodb://localhost:27017/")
mongo_db = mongo_client["yumyums"]
mongo_collection = mongo_db["non_token_question"]

# faq 컬렉션의 모든 문서 삭제
mongo_collection.delete_many({})

# 연결 종료
mongo_client.close()

print("faq 컬렉션의 모든 문서가 삭제되었습니다.")
