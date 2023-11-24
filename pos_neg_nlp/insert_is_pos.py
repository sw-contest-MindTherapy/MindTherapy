import re
import json
from konlpy.tag import Okt
from keras.utils import pad_sequences
from keras.preprocessing.text import Tokenizer
import mysql.connector
import os
import pickle
import keras


okt = Okt()
tokenizer  = Tokenizer()
os.environ['TF_CPP_MIN_LOG_LEVEL'] = '4'

DATA_CONFIGS = 'data_configs.json'
prepro_configs = json.load(open('C:/Users/82107/Desktop/Study/SW_contest_mindtherapy/pos_neg_nlp/CLEAN_DATA/'+DATA_CONFIGS,'r')) #데이터 경로 설정

#데이터 경로 설정
with open('C:/Users/82107/Desktop/Study/SW_contest_mindtherapy/CLEAN_DATA/tokenizer.pickle','rb') as handle:
    word_vocab = pickle.load(handle)

prepro_configs['vocab'] = word_vocab

tokenizer.fit_on_texts(word_vocab)

MAX_LENGTH = 8 #문장최대길이

# MySQL 연결 설정하기
cnx = mysql.connector.connect(user=os.getenv('DB_USER'), password=os.getenv('DB_PASS'),
                              host=os.getenv('DB_HOST'), database=os.getenv('DB_NAME'))

cursor = cnx.cursor()

model = keras.models.load_model('C:/Users/82107/Desktop/Study/SW_contest_mindtherapy/pos_neg_nlp/my_models/') #데이터 경로 설정
model.load_weights('C:/Users/82107/Desktop/Study/SW_contest_mindtherapy/pos_neg_nlp/DATA_OUT/cnn_classifier_kr/weights.h5') #데이터 경로 설정

select_cursor = cnx.cursor(buffered=True)
update_cursor = cnx.cursor()

# client_comment 테이블에서 데이터 조회하기
select_query = "SELECT client_key, body FROM client_comment"
select_cursor.execute(select_query)

results = select_cursor.fetchall()

processed_count = 0

for (client_key, body) in results:
    # body 값으로 감성 분석 수행하기
    
    sentence = re.sub(r'[^ㄱ-ㅎㅏ-ㅣ가-힣\\s ]','', body)
    stopwords = ['은','는','이','가','하','아','것','들','의','있','되','수','보',
                 '주', '등', '한']  # 불용어 추가할 것이 있으면 이곳에 추가
    sentence = okt.morphs(sentence, stem=True)  # 토큰화

    if len(sentence) == 0:  # 토큰화 결과 확인하기
        continue

    sentence = [word for word in sentence if not word in stopwords]  # 불용어 제거

    if len(sentence) == 0:  # 불용어 제거 결과 확인하기
        continue

    vector  = tokenizer.texts_to_sequences(sentence)

    if len(vector) == 0 or all(len(v) == 0 for v in vector): 
        continue

    pad_new = pad_sequences(vector, maxlen=MAX_LENGTH)  # 패딩


    predictions = model.predict(pad_new)
    predictions = float(predictions[0])

    if len(pad_new) == 0:  # 'pad_new'가 비어 있는지 확인하기
        continue  # 비어 있다면 이번 반복을 건너뛰기

    emotion = 'positive' if predictions > 0.5 else 'negative'

     # 감성 분석 결과를 emotion 열에 업데이트하기
    update_query="UPDATE client_comment SET emotion=%s WHERE client_key=%s"
    update_cursor.execute(update_query,(emotion,client_key))

    processed_count += 1
    if processed_count % 100 == 0:
        print(f"{processed_count} records have been processed.")

cnx.commit()  

select_cursor.close()
update_cursor.close()
cnx.close()

    