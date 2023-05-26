import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import seaborn as sns

# Load dataset
cereal_df = pd.read_csv("D:\Coding\Python\Data Visualization\cereal.csv")

print(cereal_df.head())

#irrelevant fields
fields = ['shelf', 'weight', 'cups', 'rating']
cereal_df_new = cereal_df.drop(fields, axis = 1)

print(cereal_df_new.head())

#corr()
cereal_corr = cereal_df_new.corr() # Get correlation data
print(cereal_corr)

# ones_like can build a matrix of booleans (True, False) with the same shape as our data
ones_corr = np.ones_like(cereal_corr, dtype=bool)
print(ones_corr)

# np's triu: return only upper triangle matrix
mask = np.triu(ones_corr)
print(mask)

#sns.heatmap(data=cereal_corr, mask=mask)


# When removing the upper triangle, here are still 2 empty cells in our matrix (calories & vitamin)
adjusted_mask = mask[1:, :-1]
adjusted_cereal_corr = cereal_corr.iloc[1:, :-1]

fig, ax = plt.subplots(figsize=(10,8))

# That method uses HUSL colors, so you need hue, sturation, and lightness.
# I used hsluv.org to select the colors of this chart.
cmap = sns.diverging_palette(0, 230, 90, 60, as_cmap=True)
sns.heatmap(data=adjusted_cereal_corr, mask=adjusted_mask, annot=True, annot_kws={"fontsize":13}, fmt=".2f", cmap=cmap, vmin=-1, vmax=1, linecolor="white", linewidths=0.5)
yticks = [i.upper() for i in adjusted_cereal_corr.index]
xticks = [i.upper() for i in adjusted_cereal_corr.columns]
ax.set_yticklabels(yticks, rotation=0, fontsize=13)
ax.set_xticklabels(xticks, rotation=90, fontsize=13)
title = 'CORRELATION MATRIX\nSAMPLED CEREALS COMPOSITION\n'
ax.set_title(title, loc="left", fontsize=18)
plt.show()
