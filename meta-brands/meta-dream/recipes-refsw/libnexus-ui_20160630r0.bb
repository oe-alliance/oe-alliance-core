require libnexus-ui.inc

RDEPENDS_${PN} = "bcmdriver"

SRC_URI[dm520.md5sum] = "9866c160715e8d9b1bfdf1dd3f27a298"
SRC_URI[dm520.sha256sum] = "07d9a68c73d6097e86d885631d738dcefcf032758a8661d265e487c0c0f81eed"
SRC_URI[dm900.md5sum] = "2429e397f2c3b5d843b8de00b18e5a52"
SRC_URI[dm900.sha256sum] = "5cfbdd9ed6bdc7cfcfd0882d67b918cb67af4533328b09dfb192ea0ba034af6b"

inherit opendreambox-precompiled-binary3
