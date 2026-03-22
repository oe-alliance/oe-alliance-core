# OpenBh image hooks
MAINTAINER = "OpenBh"

PR_NUM = "${@bb.utils.contains("DISTRO_TYPE", "release", "${BUILD_VERSION}.000", "${BUILD_VERSION}.${DEVELOPER_BUILD_VERSION}", d)}"
PR = "r${PR_NUM}"
