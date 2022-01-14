from fuzzywuzzy import fuzz 
from fuzzywuzzy import process
import pandas as pd
import numpy as np

from os.path import dirname, join
filename = join(dirname(__file__),"test.csv")
df = pd.read_csv(filename)
df = df[['TranslatedRecipeName']]


def prediction(input_):
    list1 = []
    
    recepie_list = df['TranslatedRecipeName'].unique()
    query = input_
    choices = recepie_list

    out_put = process.extract(query, choices)
    # print(out_put)

    for i in out_put:
        list1.append(i[0])
    
    return list1





# print(prediction('maida, oil, yoghurt'))
