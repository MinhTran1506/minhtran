import pandas as pd
import re


df = pd.read_csv('glassdoor_jobs.csv')
df1 = pd.read_csv('glassdoor_jobs.csv')

# Salary parsing

df['Hourly'] = df['Salary Estimate'].apply(lambda x: 1 if 'per hour' in x.lower() else 0)
df['Employer_provided'] = df['Salary Estimate'].apply(lambda x: 1 if 'employer provided' in x.lower() else 0)

df = df[df['Salary Estimate'] != '-1'] 
salary = df['Salary Estimate'].apply(lambda x: x.split('(')[0])
minus_Kd = salary.apply(lambda x: x.replace('K', '').replace('$', ''))

min_hr = minus_Kd.apply(lambda x: x.lower().replace('per hour', '').replace('employer provided salary:', ''))

# Min/Max/Average salary
df['min_salary'] = min_hr.apply(lambda x: int(x.split('-')[0]))
df['max_salary'] = min_hr.apply(lambda x: int(x.split('-')[1]))
df['avg_salary'] = (df.min_salary + df.max_salary) / 2



print(df['min_salary'].dtype)

# Drop duplicates
df.drop_duplicates(inplace=True)


# Company name text only
pattern = r'\d+\.\d+'
df['Company Name'] = [re.sub(pattern, '', line) for line in df['Company Name']]

# Job state
df['job_state'] = df['Location'].apply(lambda x: x.split(',')[-1])
df['same_state'] = df.apply(lambda x: 1 if x.Location == x.Headquarters else 0, axis=1)
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

df = df.drop(['Unnamed: 0'], axis=1)

df.to_csv("Clean_DataScientistSalary.csv", index=False)
#print(minus_Kd)