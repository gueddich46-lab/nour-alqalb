from PIL import Image, ImageDraw
from pathlib import Path

S=512
im=Image.new('RGBA',(S,S),(8,27,37,255))
d=ImageDraw.Draw(im)
# Deep navy rounded square, elegant golden frame.
d.rounded_rectangle((13,13,499,499),radius=112,fill=(11,42,54),outline=(226,189,116),width=15)
# Large golden crescent: legible at small Android launcher sizes.
d.ellipse((112,86,408,382),fill=(243,205,127))
d.ellipse((177,51,454,328),fill=(11,42,54))
# Small guiding star.
d.polygon([(383,101),(395,132),(427,144),(395,155),(383,187),(372,155),(339,144),(372,132)],fill=(255,235,169))
# Tiny subtle dot ornaments, no unreadable lettering.
d.ellipse((171,411,184,424),fill=(226,189,116))
d.ellipse((250,418,263,431),fill=(226,189,116))
d.ellipse((329,411,342,424),fill=(226,189,116))
p=Path('app/src/main/res/drawable')
p.mkdir(parents=True,exist_ok=True)
im.save(p/'app_icon.png')
print('Created simple crescent Android launcher icon')
