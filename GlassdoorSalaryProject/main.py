import glassdoor_scraping as gs
import pandas as pd

path = "D:/Scholarship/Portfolio/tranquangminh.github.io/GlassdoorSalaryProject/chromedriver.exe"

#df = gs.get_jobs('data scientist', 10, path, 1)
#df.to_csv('DataScientistSalary.csv', index = False)

df = pd.read_csv('DataScientistSalary.csv')
print(df.head(10))