DESCRIPTION = "E2OpenPlugins Task"
PR = "r5"

inherit task

DEPENDS = " \
	enigma2-plugin-extensions-addstreamurl \
	enigma2-plugin-extensions-antilogo \
	enigma2-plugin-extensions-bitrate \
	enigma2-plugin-extensions-buienradar \
	enigma2-plugin-extensions-changerootpassword \
	enigma2-plugin-extensions-foreca \
	enigma2-plugin-extensions-meteoitalia2 \
	enigma2-plugin-extensions-multiquickbutton \
	enigma2-plugin-extensions-newsreader \
	enigma2-plugin-extensions-oggisport \
	enigma2-plugin-extensions-openwebif \
	enigma2-plugin-extensions-oroscopoitalia \
	enigma2-plugin-extensions-permanentvfdclock \
	enigma2-plugin-extensions-ppanel \
	enigma2-plugin-extensions-remotecontrolchannel \
	enigma2-plugin-extensions-remotestreamconvert \
	enigma2-plugin-extensions-shootyourscreen \
	enigma2-plugin-extensions-simpleumount \
	enigma2-plugin-extensions-streaminterface \
	enigma2-plugin-extensions-wakeonlan \
	enigma2-plugin-systemplugins-autoshutdown \
	enigma2-plugin-systemplugins-crossepg \
"

require assume-gplv2.inc
