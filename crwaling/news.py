from selenium import webdriver
import requests
import time
import datetime
# import pandas

# 해야할것들
# 실시간 크롤링 ex) 한시간에 한번, 경제, 사회, IT과학기사별, 기사본문 크롤링, 
# 헤드라인 url


# driver = webdriver.Chrome(executable_path="C:\\Program Files\\chromedriver\\chromedriver.exe")
# driver = webdriver.Chrome("C:\\Users\\mycom\\Desktop\\new\\chromedriver.exe")
driver = webdriver.Chrome(executable_path="C:\\Program Files\\chromedriver\\chromedriver.exe")
driver.get("https://news.naver.com/main/main.nhn?mode=LSD&mid=shm&sid1=105")
driver.find_element_by_xpath("//*[@id='main_content']/div/div[2]/div[2]/div/a").click()

dt = datetime.datetime.now()
filename = 'news_' + dt.strftime("%Y_%m_%d_%H_%M")

f = open(filename +'.txt','w',encoding='utf-8')

#헤드라인 날짜
# days = driver.find_elements_by_css_selector('#header > div.lnb > span')
# for day in range(len(days)):
#     print(days[day].text)
f.write(dt.strftime("%Y%m%d%H")+"\n")
    
# 헤드라인
titles = driver.find_elements_by_css_selector('.cluster_head_inner > div > h2 > a > span')
headline_urls = driver.find_elements_by_css_selector('.cluster_head_inner > div > h2 > a')
f.write("HEAD LINE")
f.write("--------------------------\n")

for i in range(0, len(titles),2 ):
    try:
        print(i//2+1, titles[i].text.rstrip()+', '+titles[i+1].text.strip())
        url = headline_urls[i//2+1].get_attribute('href')
        print('해당기사 URL :'+ url+'\n')
        f.write("["+titles[i].text.rstrip()+ ', '+titles[i+1].text.strip() +"]\n")
        f.write('해당기사 :' + url + '\n')
    except:
        pass
f.write("--------------------------\n")


for j in range(1,2):
    driver.get("https://news.naver.com/main/main.nhn?mode=LSD&mid=shm&sid1=105" + "#&date=2000:00:00" + "&page=" + str(j))
    time.sleep(2)
    driver.implicitly_wait(60)


    # 메인뉴스
    main = driver.find_elements_by_css_selector('#section_body > ul > li > dl > dt:nth-child(2)')
    main_url = driver.find_elements_by_css_selector('#section_body > ul > li > dl > dt:nth-child(2) > a')
    # print(len(main_url))
    print(main_url)
    print(main)

    
    k = 0
    while k < len(main_url):
        url = main_url[k].get_attribute('href')
        print(main_url[k].text)         # 제목
        f.write("Main Title : ["+ main_url[k].text+"]\n")
        print(url)    # url
        f.write("news URL : [" + url +"]\n")
        driver.get(url)
        time.sleep(5)
        driver.implicitly_wait(60)
        article = driver.find_element_by_css_selector('#articleBodyContents')
        print(article.text)
        f.write("본문 : [" + article.text + "]\n")
        driver.get("https://news.naver.com/main/main.nhn?mode=LSD&mid=shm&sid1=105" + "#&date=2000:00:00" + "&page=" + str(j))
        main_url = driver.find_elements_by_css_selector('#section_body > ul > li > dl > dt:nth-child(2) > a')
        
        k += 1

    f.close()