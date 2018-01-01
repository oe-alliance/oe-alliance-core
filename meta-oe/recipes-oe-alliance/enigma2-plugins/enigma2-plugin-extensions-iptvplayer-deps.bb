SUMMARY = "Meta package for installing all dependencies for SSS' IPTV Player"
inherit allarch

require conf/license/license-gplv2.inc

RRECOMMENDS_${PN} = " \
	ffmpeg \
	exteplayer3 \
	gstplayer \
	wget \
	hlsdl \
	lsdir \
	f4mdump \
	gst-ifdsrc \
	iptvsubparser \
	rtmpdump \
	duktape \
	uchardet \
	"

PV = "1.0"
PR = "r0"

ALLOW_EMPTY_${PN} = "1"
