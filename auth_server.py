import json
import requests
import firebase_admin
from firebase_admin import auth
from firebase_admin import credentials
from flask import Flask, request, jsonify

app = Flask(__name__)

@app.route('/auth', methods=['POST'])
def verify():
    data = request.get_json()
    id_token = data['id_token']
    try:
        decoded_token = auth.verify_id_token(id_token)
        uid = decoded_token['uid']
        return jsonify({'uid': uid}), 200
    except:
        return jsonify({'error': 'Invalid token'}), 401

def init_firebase():
    cred = credentials.Certificate('yallanow12-firebase-adminsdk-njuxe-db0348de3f.json')
    firebase_admin.initialize_app(cred)


if __name__ == '__main__':
    init_firebase()
    app.run(port=5001)

