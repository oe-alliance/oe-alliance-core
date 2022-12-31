require recovery-web.inc

SRCREV = "5d68fcde19886c1fdf4987ec18ceaa346a40c68a"
SRCREV:dm520 = "b181468778f1e5ba95fb2185c9338d6b08122625"
SRCREV:dm820 = "dm7080"
SRCREV:dm900 = "1e17339e15c8e77b03ae981989111c7c62b923d7"
SRCREV:dm920 = "dm900"
SRCREV:dm7080 = "3f02d9e7eeb29248ea1984730fdcbd3ceb63a726"
SRCREV:dreamone = "ffaa96d0e62568aa525728de98ffc10a07c3b8ec"
SRCREV:dreamtwo = "dreamone"

inherit opendreambox-git

BRANCH = "master"
BRANCH:dm520 = "dm520"
BRANCH:dm820 = "dm7080"
BRANCH:dm900 = "dm900"
BRANCH:dm920 = "dm900"
BRANCH:dm7080 = "dm7080"
BRANCH:dreamone = "dreamone"
BRANCH:dreamtwo = "dreamone"

COMPATIBLE_MACHINE = "^(dm520|dm820|dm900|dm920|dm7080|dreamone|dreamtwo)$"
