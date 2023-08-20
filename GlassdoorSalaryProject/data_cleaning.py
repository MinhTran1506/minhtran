import pandas as pd
import re


df = pd.read_csv('DataScientistSalary.csv')


# Drop missing value
df = df.dropna(subset=['Salary Estimate', 'Job Description', 'Company Name', 'Location', 'Size', 'Founded', 'Sector', 'Revenue', 'Industry', 'Type'])

# Salary parsing
salary = df['Salary Estimate'].apply(lambda x: x.split(' ')[0])
minus_Kd = salary.apply(lambda x: x.replace('$', ''))
minus_comma = minus_Kd.apply(lambda x: x.replace(',', ''))

df['Hourly'] = df['Salary Estimate'].apply(lambda x: 1 if 'hr' in x.lower() else 0)

df['Salary Estimate'] = minus_comma
df['Salary Estimate'] = df['Salary Estimate'].apply(lambda  x: float(x))
df['Salary Estimate'].dtype

# Drop duplicates
df.drop_duplicates(inplace=True)

# Company name text only
pattern = r'\d+\.\d+'
df['Company Name'] = [re.sub(pattern, '', line) for line in df['Company Name']]

# Job state
df['job_state'] = df['Location'].apply(lambda x: x.split(',')[-1])

# Company Age
df['Age'] = df['Founded'].apply(lambda x: x if x < 1 else 2023 - x)

# Python
df['python_yn'] = df['Job Description'].apply(lambda x: 1 if 'python' in x.lower() else 0)

# R Sudio
df['R_yn'] = df['Job Description'].apply(lambda x: 1 if 'r studio' in x.lower() or 'r-studio' in x.lower() else 0)

# Spark
df['spark_yn'] = df['Job Description'].apply(lambda x: 1 if 'spark' in x.lower() else 0)

# AWS
df['aws_yn'] = df['Job Description'].apply(lambda x: 1 if 'aws' in x.lower() else 0)

# Excel
df['excel'] = df['Job Description'].apply(lambda x: 1 if 'excel' in x.lower() else 0)



print(df.head(10))
print(df.python_yn.value_counts())
print(df.R_yn.value_counts())

df.to_csv("Clean_DataScientistSalary.csv", index=False)
#print(minus_Kd)