from flask import Flask, render_template, request
from flask_cors import CORS
from logic.srcAns import src_sentence
from logic.srcAnsList import src_sentence_list

app = Flask(__name__)
CORS(app, resources={r"/*": {"origins": "*"}})  # 모든 출처에서의 요청을 허용

@app.route("/")
def hello_world():
    return "<a>ocr_home</a>"

@app.route('/embedding', methods=['GET'])
def home():
    question = request.args.get("question", None)
    return src_sentence(question)

@app.route('/list', methods=['GET'])
def list_items():
    question = request.args.get("question", None)
    return src_sentence_list(question)

if __name__ == '__main__':
    app.run(host='192.168.0.106', port=5000, debug=True)
