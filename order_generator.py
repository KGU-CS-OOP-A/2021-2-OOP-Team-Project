import hashlib
import random
import datetime

text='저기요'
method=''
ordermenu=''
tel=['01088606781','01073248716','01078214867','01010069929','01023711572','01036228484','01008659421']
menu=['R0746','R6133','R3747','R5320','R2243','D8353','D8542','S0771','S9799']
price=[8500,9000,9000,10000,8000,1500,1500,2000,3000]

for i in range(50):
    day=random.randint(1,30)
    hour=random.randint(12,23)
    minute=random.randint(0,59)
    second=random.randint(0,59)
    date=datetime.datetime(2021,11,day,hour,minute,second)
    date='_'.join(str(date).split())

    randtel=random.randint(0,6)

    select=random.randint(0,2)
    if select==0:
        method='card'
    elif select==1:
        method='cash'
    elif select==2:
        method='coupon'

    menunum=random.randint(1,5)
    ordermenu=""
    total=0
    for i in range(menunum):
        randmenu=random.randint(0,8)
        ordercount=random.randint(1,5)
        total=total+price[randmenu]*ordercount
        ordermenu=ordermenu+"/"+menu[randmenu]+"_"+str(ordercount)

    
    tmp=text+str(i)
    code=hashlib.md5(tmp.encode()).hexdigest()

    result=date+"/"+tel[randtel]+"/"+method+"/"+str(total)+"/"+code[0:10]+ordermenu
    print(result)

text='저기요'
method=''
ordermenu=''
tel=['01088606781','01073248716','01078214867','01010069929','01023711572','01036228484','01008659421']
menu=['R0746','R6133','R3747','R5320','R2243','D8353','D8542','S0771','S9799']
price=[8500,9000,9000,10000,8000,1500,1500,2000,3000]

for i in range(50):
    day=random.randint(1,30)
    hour=random.randint(17,20)
    minute=random.randint(0,59)
    second=random.randint(0,59)
    date=datetime.datetime(2021,11,day,hour,minute,second)
    date='_'.join(str(date).split())

    randtel=random.randint(0,6)

    select=random.randint(0,2)
    if select==0:
        method='card'
    elif select==1:
        method='cash'
    elif select==2:
        method='coupon'

    menunum=random.randint(1,5)
    ordermenu=""
    total=0
    for i in range(menunum):
        randmenu=random.randint(0,8)
        ordercount=random.randint(1,5)
        total=total+price[randmenu]*ordercount
        ordermenu=ordermenu+"/"+menu[randmenu]+"_"+str(ordercount)

    
    tmp=text+str(i)
    code=hashlib.md5(tmp.encode()).hexdigest()

    result=date+"/"+tel[randtel]+"/"+method+"/"+str(total)+"/"+code[0:10]+ordermenu
    print(result)

text='배종'
for i in range(100):
    tmp=text+str(i)
    code=hashlib.md5(tmp.encode()).hexdigest()
    
    day=random.randint(1,30)
    hour=random.randint(12,23)
    minute=random.randint(0,59)
    second=random.randint(0,59)
    date=datetime.datetime(2021,11,day,hour,minute,second)

    select=random.randint(0,2)
    if select==0:
        method='card'
    elif select==1:
        method='cash'
    elif select==2:
        method='coupon'

    randtel=random.randint(0,6)

    menunum=random.randint(1,5)
    ordermenu=str(menunum)
    for i in range(menunum):
        randmenu=random.randint(0,8)
        ordercount=random.randint(1,5)
        ordermenu=ordermenu+"|"+menu[randmenu]+"|"+str(ordercount)

    result=code[0:10]+"|"+str(date)+"|"+method+"|"+tel[randtel]+"|"+ordermenu
    
    print(result)

text='배종'
for i in range(100):
    tmp=text+str(i)
    code=hashlib.md5(tmp.encode()).hexdigest()
    
    day=random.randint(1,30)
    hour=random.randint(17,20)
    minute=random.randint(0,59)
    second=random.randint(0,59)
    date=datetime.datetime(2021,11,day,hour,minute,second)

    select=random.randint(0,2)
    if select==0:
        method='card'
    elif select==1:
        method='cash'
    elif select==2:
        method='coupon'

    randtel=random.randint(0,6)

    menunum=random.randint(1,5)
    ordermenu=str(menunum)
    for i in range(menunum):
        randmenu=random.randint(0,8)
        ordercount=random.randint(1,5)
        ordermenu=ordermenu+"|"+menu[randmenu]+"|"+str(ordercount)

    result=code[0:10]+"|"+str(date)+"|"+method+"|"+tel[randtel]+"|"+ordermenu
    
    print(result)

text='이츠'
for i in range(50):
    tmp=text+str(i)
    code=hashlib.md5(tmp.encode()).hexdigest()
    
    day=random.randint(1,30)
    hour=random.randint(12,23)
    minute=random.randint(0,59)
    second=random.randint(0,59)
    #date=datetime.datetime(2021,11,day,hour,minute,second)
    date="202111"+str(day).zfill(2)+str(hour).zfill(2)+str(minute).zfill(2)+str(second).zfill(2)

    select=random.randint(0,2)
    if select==0:
        method='CRD'
    elif select==1:
        method='CSH'
    elif select==2:
        method='CPN'

    randtel=random.randint(0,6)

    menunum=random.randint(1,5)
    ordermenu=str(menunum)
    total=0
    for i in range(menunum):
        randmenu=random.randint(0,8)
        ordercount=random.randint(1,5)
        total=total+price[randmenu]*ordercount
        ordermenu=ordermenu+" "+menu[randmenu]+" "+str(ordercount)

    result=code[0:10]+" "+tel[randtel]+" "+method+" "+str(total)+" "+str(date)+" "+ordermenu    
    print(result)

text='이츠'
for i in range(50):
    tmp=text+str(i)
    code=hashlib.md5(tmp.encode()).hexdigest()
    
    day=random.randint(1,30)
    hour=random.randint(17,20)
    minute=random.randint(0,59)
    second=random.randint(0,59)
    #date=datetime.datetime(2021,11,day,hour,minute,second)
    date="202111"+str(day).zfill(2)+str(hour).zfill(2)+str(minute).zfill(2)+str(second).zfill(2)

    select=random.randint(0,2)
    if select==0:
        method='CRD'
    elif select==1:
        method='CSH'
    elif select==2:
        method='CPN'

    randtel=random.randint(0,6)

    menunum=random.randint(1,5)
    ordermenu=str(menunum)
    total=0
    for i in range(menunum):
        randmenu=random.randint(0,8)
        ordercount=random.randint(1,5)
        total=total+price[randmenu]*ordercount
        ordermenu=ordermenu+" "+menu[randmenu]+" "+str(ordercount)

    result=code[0:10]+" "+tel[randtel]+" "+method+" "+str(total)+" "+str(date)+" "+ordermenu     
    
    print(result)
