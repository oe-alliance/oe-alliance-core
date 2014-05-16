import os
if os.path.exists('/dev/lcd2') and os.path.exists('/usr/share/lcdbootlogo.png'): # VuDuo2 lcd
	from fcntl import ioctl
	led_fd = open("/dev/lcd2",'rw')
	ioctl(led_fd, 0x10, 25)
	led_fd.close()

	from pngutil import png_util
	pngutil = png_util.PNGUtil()
	pngutilconnect = pngutil.connect()
	if pngutilconnect:
		pngutil.send("/usr/share/lcdbootlogo.png")
