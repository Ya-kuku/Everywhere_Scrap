from selenium import webdriver
import requests
import time
# import pandas

# 해야할것들
# 실시간 크롤링 ex) 한시간에 한번, 경제, 사회, IT과학기사별, 기사본문 크롤링, 


# driver = webdriver.Chrome(executable_path="C:\\Program Files\\chromedriver\\chromedriver.exe")
driver = webdriver.Chrome(executable_path="C:\\Program Files\\chromedriver\\chromedriver.exe")
driver.get("https://news.naver.com/main/main.nhn?mode=LSD&mid=shm&sid1=105")
driver.find_element_by_xpath("//*[@id='main_content']/div/div[2]/div[2]/div/a").click()

# 헤드라인
titles = driver.find_elements_by_css_selector('.cluster_head_inner > div > h2 > a > span')
for i in range(0, len(titles),2 ):
    print(i//2+1, titles[i].text.rstrip()+', '+titles[i+1].text.strip())

for j in range(1,15):
    driver.get("https://news.naver.com/main/main.nhn?mode=LSD&mid=shm&sid1=105" + "#&date=2000:00:00" + "&page=" + str(j))
    time.sleep(2)
    driver.implicitly_wait(60)


    # 메인뉴스
    main = driver.find_elements_by_css_selector('#section_body > ul > li > dl > dt:nth-child(2)')
    for i in range(len(main)):
        print(main[i].text)
    