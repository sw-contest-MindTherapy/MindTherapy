from sklearn.feature_extraction.text import TfidfVectorizer
from collections import defaultdict
import numpy as np
from konlpy.tag import Okt
import re
import pprint
import os

def get_stopwords():
    stopwords = list()
    path = os.path.dirname(os.path.abspath(__file__))
    file_path = os.path.join(path,"stopwords.txt")

    f = open(file_path, 'r', encoding='utf-8')

    while True:
        line = f.readline()
        if not line: break
        stopwords.append(line.strip())
        
    return stopwords

def okt_tokenizer(text):
    okt = Okt()
    
    text = re.sub(r'[^ ㄱ-ㅣ가-힣A-Za-z]', '', text) # 특수기호 제거
    stopwords = get_stopwords() # 불용어
    
    return [token for token in okt.nouns(text)
            if len(token) > 1 and token not in stopwords]

def extract_keywords(text):         
    vectorizer = TfidfVectorizer(tokenizer=okt_tokenizer)
    
    try:
        matrix = vectorizer.fit_transform(text)
    except ValueError:
        print("분석할 형태소가 없습니다.")
        return []
    
    # 단어 사전: {"token": id}
    vocabulary_word_id = defaultdict(int)
    
    for idx, token in enumerate(vectorizer.get_feature_names_out()):
        vocabulary_word_id[token] = idx
    
    # 특징 추출 결과: {"token": value}
    result = defaultdict(str)
    
    for token in vectorizer.get_feature_names_out():
        result[token] = matrix[0, vocabulary_word_id[token]]
    
    # 내림차순 (중요도 high) 기준 정렬
    result = sorted(result.items(), key = lambda item: item[1], reverse = True)
    
    return [token for token, value in result if not value < 0.1]



def get_user_input():
    user_input = input("분석하고 싶은 텍스트를 입력하세요: ")
    return user_input

def main():
    text = get_user_input()
    keywords = extract_keywords([text]) # 리스트로 입력
    print(keywords)

if __name__ == "__main__":
    main()
