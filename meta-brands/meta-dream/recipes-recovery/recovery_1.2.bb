require recovery.inc

SRCREV = "81ff8d40bbe21ec4bff1e397e4afdd2f1f3337e3"
SRCREV:dreamone = "f7d7761af56c4f8d79815dd82e937061a31a94d5"
SRCREV:dreamtwo = "f7d7761af56c4f8d79815dd82e937061a31a94d5"

inherit opendreambox-git

BRANCH = "master"

COMPATIBLE_MACHINE = "^(dm520|dm820|dm900|dm920|dm7080|dreamone|dreamtwo)$"
