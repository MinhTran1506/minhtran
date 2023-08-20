import glassdoor_scraper as gs
import pandas as pd

path = "D:/Scholarship/Portfolio/tranquangminh.github.io/GlassdoorSalaryProject/chromedriver.exe"

df = gs.get_jobs('data scientist', 10, path, 1)
df.to_csv('DataScientistSalary.csv', index = False)

