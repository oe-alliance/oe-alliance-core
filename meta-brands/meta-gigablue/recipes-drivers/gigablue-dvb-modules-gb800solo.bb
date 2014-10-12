SRCDATE = "20140904"

KV = "3.9.6"

SRC_URI[md5sum] = "e45a9d110c42ff000df7c76a0d11849c"
SRC_URI[sha256sum] = "910fafe43fbe0c46ed7c437454627a2fa2bdf1f92b0fbdb3f56225f3c5696319"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-gb800xx-${SRCDATE}.zip"

require gigablue-dvb-modules.inc