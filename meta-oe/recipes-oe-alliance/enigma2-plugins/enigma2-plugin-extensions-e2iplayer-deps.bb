SUMMARY = "Meta package for installing all dependencies for SSS' E2iPlayer"
inherit allarch

require conf/license/license-gplv2.inc

RRECOMMENDS_${PN} = " \
	cmdwrap \
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
PR = "r1"

ALLOW_EMPTY_${PN} = "1"
