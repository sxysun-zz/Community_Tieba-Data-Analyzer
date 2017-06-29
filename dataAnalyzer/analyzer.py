import  jieba

file = open("titles.txt");
jieba.load_userdict("dict_neo.txt")

output = open('processed.txt', 'w+')

while 1:
    line = file.readline()
    if not line:
        break
    seg = jieba.cut(line, cut_all=False)
    output.write("/".join(seg))
    # print("/".join(seg))

