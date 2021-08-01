FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

DEPENDS:remove = "virtual/db"

SRC_URI += "file://fix-build-openssl102q.patch"
