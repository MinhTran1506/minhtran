from selenium.common.exceptions import NoSuchElementException, ElementClickInterceptedException
from selenium import webdriver
import time
import pandas as pd
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC


def get_jobs(keyword, num_pages, path):
    
    '''Gathers jobs as a dataframe, scraped from Glassdoor'''
    
    #Initializing the webdriver
    options = webdriver.ChromeOptions()
    
    #Uncomment the line below if you'd like to scrape without a new Chrome window every time.
    #options.add_argument('headless')
    
    #Change the path to where chromedriver is in your home folder.
    driver = webdriver.Chrome(executable_path=path, options=options)
    driver.set_window_size(1120, 1000)
    
    url = "https://www.glassdoor.com/Job/jobs.htm?suggestCount=0&suggestChosen=false&clickSource=searchBtn&typedKeyword="+keyword+"&sc.keyword="+keyword+"&locT=&locId=&jobType="
    #url = 'https://www.glassdoor.com/Job/jobs.htm?sc.keyword="' + keyword + '"&locT=C&locId=1147401&locKeyword=San%20Francisco,%20CA&jobType=all&fromAge=-1&minSalary=0&includeNoSalaryJobs=true&radius=100&cityId=-1&minRating=0.0&industryId=-1&sgocId=-1&seniorityType=all&companyId=-1&employerSizes=0&applicationType=0&remoteWorkType=0'
    driver.get(url)
    company_name = []
    job_title = []
    location = []
    job_description = []
    salary_estimate = []
    company_size = []
    company_type = []
    company_sector = []
    company_industry = []
    company_founded = []
    company_revenue = []
    rating = []
    current_page = 1
    processed_cards = set()

    while current_page <= num_pages:  #If true, should be still looking for new jobs.

        #Let the page load. Change this number based on your internet speed.
        #Or, wait until the webpage is loaded, instead of hardcoding it.
        #time.sleep(slp_time)

        #Test for the "Sign Up" prompt and get rid of it.
        #try:
        #    driver.find_element_by_class_name("selected").click()
        #except ElementClickInterceptedException:
        #    pass

        #time.sleep(.1)

        

        done = False
        while not done:
            job_cards = driver.find_elements_by_xpath("//article[@id='MainCol']//ul/li[@data-adv-type='GENERAL']")
            for card in job_cards:
                card.click()
                processed_cards.add(card)
                time.sleep(1)
                
                try:
                    close_button = driver.find_element_by_css_selector('button[data-role-variant="ghost"] > div.css-raue2m.e1fx8g6d0')
                    driver.execute_script("arguments[0].click();", close_button)
                    print(' x out worked')
                except NoSuchElementException:
                    #print(' x out failed')
                    pass

                #Expands the Description section by clicking on Show More
                try:
                    driver.find_element_by_xpath("//div[@class='css-t3xrds e856ufb4']").click()
                    time.sleep(1)
                except NoSuchElementException:
                    card.click()
                    print(str(current_page) + '#ERROR: no such element')
                    time.sleep(30)
                    driver.find_element_by_xpath("//div[@class='css-t3xrds e856ufb4']").click()
                except ElementClickInterceptedException:
                    card.click()
                    driver.implicitly_wait(30)
                    print(str(current_page) + '#ERROR: not interactable')
                    driver.find_element_by_xpath("//div[@class='css-t3xrds e856ufb4']").click()
                

                #Scrape 

                try:
                    #company_name.append(driver.find_element_by_xpath("//div[@class='css-xuk5ye e1tk4kwz5']").text)
                    company_name.append(driver.find_element_by_xpath("//div[@class='css-87uc0g e1tk4kwz1']").text)
                except:
                    company_name.append("#N/A")
                    pass

                try:
                    job_title.append(driver.find_element_by_xpath("//div[@class='css-1vg6q84 e1tk4kwz4']").text)
                except:
                    job_title.append("#N/A")
                    pass

                try:
                    location.append(driver.find_element_by_xpath("//div[@class='css-56kyx5 e1tk4kwz5']").text)
                except:
                    location.append("#N/A")
                    pass

                try:
                    job_description.append(driver.find_element_by_xpath("//div[@id='JobDescriptionContainer']").text)
                except:
                    job_description.append("#N/A")
                    pass

                try:
                    #salary_estimate.append(driver.find_element_by_xpath("//div[@class='css-1bluz6i e2u4hf13']").text)
                    salary_estimate.append(driver.find_element_by_xpath("//span[@class='css-1xe2xww e1wijj242']").text)

                except:
                    salary_estimate.append("#N/A")
                    pass
                try:
                    rating.append(driver.find_element_by_xpath("//div[@class='mr-sm css-ey2fjr e1pr2f4f2']").text)
                except:
                    rating.append("#N/A")
                    pass
                    
                try:
                    company_size.append(driver.find_element_by_xpath("//div[@id='CompanyContainer']//span[text()='Size']//following-sibling::*").text)
                except:
                    company_size.append("#N/A")
                    pass
                    
                try:
                    company_type.append(driver.find_element_by_xpath("//div[@id='CompanyContainer']//span[text()='Type']//following-sibling::*").text)
                except:
                    company_type.append("#N/A")
                    pass
                        
                try:
                    company_sector.append(driver.find_element_by_xpath("//div[@id='CompanyContainer']//span[text()='Sector']//following-sibling::*").text)
                except:
                    company_sector.append("#N/A")
                    pass
                        
                try:
                    company_industry.append(driver.find_element_by_xpath("//div[@id='CompanyContainer']//span[text()='Industry']//following-sibling::*").text)
                except:
                    company_industry.append("#N/A")
                    pass
                        
                try:
                    company_founded.append(driver.find_element_by_xpath("//div[@id='CompanyContainer']//span[text()='Founded']//following-sibling::*").text)
                except:
                    company_founded.append("#N/A")
                    pass
                        
                try:
                    company_revenue.append(driver.find_element_by_xpath("//div[@id='CompanyContainer']//span[text()='Revenue']//following-sibling::*").text)
                except:
                    company_revenue.append("#N/A")
                    pass
                done = True


        #Clicking on the "next page" button
        if done:
            print(str(current_page) + ' ' + 'out of' +' '+ str(num_pages) + ' ' + 'pages done')
            driver.find_element_by_xpath("//button[@data-test='pagination-next']").click()   
            current_page = current_page + 1
            time.sleep(4)

    driver.close()
    return pd.DataFrame({'Job Title': job_title, 
    'Salary Estimate': salary_estimate,
    'Job Description': job_description,
    'Rating': rating,
    'Company Name': company_name,
    'Location': location,
    'Size': company_size,
    'Founded': company_founded,
    'Type of ownership': company_type, 'Industry' : company_industry,
    'Sector': company_sector,
    'Revenue' : company_revenue,})