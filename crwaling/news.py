from selenium import webdriver
import requests
import time
import datetime
import mongo_connection
import summary
import re

# import pandas

# 해야할것들
# 실시간 크롤링 ex) 한시간에 한번, 경제, 사회, IT과학기사별, 기사본문 크롤링, 
def preprocessing_articles(content):
    contents_new = []

    content = content.split('\n')
    content = list(filter(lambda s: len(s) > 3, content))
    
    # 글 마지막에 항상 기자의 메일 주소가 추가되어 있다. 그 이후로 나오는 글은 불필요하므로 삭제함
    for i in range(1, len(content)):
        if '@' in content[-i]:
            break
    if i == len(content)-1:
        i = 1
    content_prep = content[:-i]

    # 너무 짧거나, 광고, copyright, 사진설명, 메일 등 불필요한 단어가 포함되어 있을 경우 불필요한 문장으로 판단하고 삭제 처리
    content_prep = list(filter(lambda s: len(s) > 30  and \
        '▶' not in s and \
            '©' not in s and \
                '▲' not in s and \
                    '사진' not in s and \
                        '@' not in s and \
                            not re.findall("기자 *$", s) and \
                                not re.findall("제공 *$", s) and \
                                    not re.findall("기자 *= *", s) , content_prep))
    
    # 언론사명이 들어간 prefix 삭제
    content_prep = list(map(lambda s: re.sub("^\[.*\]", "", s), content_prep)) #[스포츠서울]
    content_prep = list(map(lambda s: re.sub("^\(.*\)", "", s), content_prep)) #(서울=뉴시스1)
    
    contents_new.append(' '.join(content_prep))

    return contents_new[0]

# driver = webdriver.Chrome(executable_path="C:\\Program Files\\chromedriver\\chromedriver.exe")
# driver = webdriver.Chrome("C:\\Users\\mycom\\Desktop\\new\\chromedriver.exe")
driver = webdriver.Chrome(executable_path="C:\\Program Files\\chromedriver\\chromedriver.exe")
driver.get("https://news.naver.com/main/main.nhn?mode=LSD&mid=shm&sid1=102")
driver.find_element_by_xpath("//*[@id='main_content']/div/div[2]/div[2]/div/a").click()

dt = datetime.datetime.now()

# 파일 이름명
filename = 'news_' + dt.strftime("%Y_%m_%d_%H_%M")

# f = open(filename +'.txt','w',encoding='utf-8')

# 당일 날짜 시간
# f.write(dt.strftime("%Y%m%d%H")+"\n")
date = dt.strftime("%Y%m%d%H")

# 헤드라인
titles = driver.find_elements_by_css_selector('.cluster_head_inner > div > h2 > a > span')
headline_urls = driver.find_elements_by_css_selector('.cluster_head_inner > div > h2 > a')
# f.write("HEAD LINE")
# f.write("--------------------------\n")

headlineList = dict()
for i in range(0, len(titles),2 ):
    try:
        headline_content = dict()
        print(i//2+1, titles[i].text.rstrip()+', '+titles[i+1].text.strip())
        
        # 헤드라인 제목
        # f.write("["+titles[i].text.rstrip()+ ', '+titles[i+1].text.strip() +"]\n")
        # f.write('해당기사 :' + url + '\n')
        headline_content['title']=(titles[i].text.rstrip()+ ', '+titles[i+1].text.strip())
        # headline_content.append(1234)
        # 헤드라인 URL
        url = headline_urls[i//2+1].get_attribute('href')
        headline_content["url"]= url
        # print('해당기사 URL :'+ url+'\n')
    except:
        IndexError
    headlineList[str((i//2+1))] = headline_content
# f.write("--------------------------\n")
# print(headlineList)


mainList = dict()
num = 0
for j in range(1,2):
    driver.get("https://news.naver.com/main/main.nhn?mode=LSD&mid=shm&sid1=102" + "#&date=2000:00:00" + "&page=" + str(j))
    time.sleep(2)
    driver.implicitly_wait(60)

    # 메인 뉴스
    main = driver.find_elements_by_css_selector('#section_body > ul > li > dl > dt:nth-child(2)')
    main_url = driver.find_elements_by_css_selector('#section_body > ul > li > dl > dt:nth-child(2) > a')

    k = 0
    # while k < len(main_url):
    while k < 1:
        main_content = dict()   
        url = main_url[k].get_attribute('href')
        
        # 메인 뉴스 제목
        print(main_url[k].text)
        main_content['title'] = main_url[k].text       
        # f.write("Main Title : ["+ main_url[k].text+"]\n")
        
        # 메인 뉴스 URL
        # print(url)    
        main_content['url'] = url
        # f.write("news URL : [" + url +"]\n")
        driver.get(url)

        time.sleep(5)
        driver.implicitly_wait(60)
        
        # 메인 뉴스 본문
        article = driver.find_element_by_css_selector('#articleBodyContents')
        # print(article.text)
        main_content['description'] = article.text

        change_text = preprocessing_articles(article.text)
        textrank = summary.TextRank(change_text)

        word_cloud = textrank.makewordcloud()
        summary.make(word_cloud,num)

        tmp = []
        for row in textrank.summarize(4):
            print(row)
            print()
            tmp.append(row)
        main_content['summary'] = tmp
        print('keywords :',textrank.keywords())
        main_content['keyword'] = textrank.keywords()
        main_content['locate'] = './img/economy/'+str(num)+'.png'
        mainList[str(num)] = main_content

        # f.write("본문 : [" + article.text + "]\n")
        driver.get("https://news.naver.com/main/main.nhn?mode=LSD&mid=shm&sid1=102" + "#&date=2000:00:00" + "&page=" + str(j))
        main_url = driver.find_elements_by_css_selector('#section_body > ul > li > dl > dt:nth-child(2) > a')
        
        k += 1
        num += 1
mongo_connection.save(date,headlineList,mainList)
# f.close()