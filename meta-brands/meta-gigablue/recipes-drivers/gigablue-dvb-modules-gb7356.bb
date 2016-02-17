SRCDATE = "20151218"

KV = "4.0.1"

SRC_URI[md5sum] = "738e888cae51d58b382f1daaa8430cc4"
SRC_URI[sha256sum] = "0cbec9fc954b252df22952aeb1f0f8d25fa37bf2dcf42fa7bc355b6e5246784e"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-BCM7356-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
