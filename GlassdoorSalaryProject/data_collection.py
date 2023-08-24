import glassdoor_scraper as gs
import pandas as pd

path = "D:/Scholarship/Portfolio/tranquangminh.github.io/GlassdoorSalaryProject/chromedriver.exe"

df = gs.get_jobs('data scientist', 29, path)
df.to_csv('glassdoor_job_salary.csv', index = False)

