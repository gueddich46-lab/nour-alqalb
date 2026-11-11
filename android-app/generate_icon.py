from PIL import Image, ImageDraw, ImageFont
import arabic_reshaper
from bidi.algorithm import get_display
from pathlib import Path

S=512
im=Image.new('RGBA',(S,S),(8,27,37,255));d=ImageDraw.Draw(im)
d.rounded_rectangle((10,10,502,502),radius=104,fill=(10,35,48),outline=(226,189,116),width=19)
d.ellipse((185,42,328,185),fill=(226,189,116))
d.ellipse((231,22,356,145),fill=(10,35,48))
d.polygon([(365,72),(374,96),(400,98),(381,112),(387,138),(365,124),(343,138),(349,112),(330,98),(356,96)],fill=(244,213,142))
# Open book
d.polygon([(73,340),(160,310),(256,338),(352,310),(439,340),(439,417),(352,395),(256,428),(160,395),(73,417)],fill=(226,189,116))
d.polygon([(91,349),(160,331),(247,353),(247,407),(161,376),(91,396)],fill=(15,53,66))
d.polygon([(265,353),(352,331),(421,349),(421,396),(352,376),(265,407)],fill=(15,53,66))
d.line([(256,350),(256,418)],fill=(244,213,142),width=7)
font='/usr/share/fonts/truetype/dejavu/DejaVuSans-Bold.ttf'
for txt,size,y,color in [('نور القلب',65,189,(245,213,143)),('بشير قديش',48,270,(255,244,212))]:
 f=ImageFont.truetype(font,size);render=get_display(arabic_reshaper.reshape(txt));bbox=d.textbbox((0,0),render,font=f);w=bbox[2]-bbox[0];d.text(((S-w)/2,y),render,font=f,fill=color,stroke_width=1,stroke_fill=(9,26,38))
p=Path('app/src/main/res/drawable');p.mkdir(parents=True,exist_ok=True)
im.save(p/'app_icon.png')
print('Created launcher icon',p/'app_icon.png')
