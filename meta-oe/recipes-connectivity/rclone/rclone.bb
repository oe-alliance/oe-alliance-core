SUMMARY = "rsync for cloud storage"
DESCRIPTION = "Rclone is a command line program to sync files and directories to and from \
 \
Google Drive \
Amazon S3 \
Openstack Swift / Rackspace cloud files / Memset Memstore \
Dropbox \
Google Cloud Storage \
Amazon Drive \
Microsoft OneDrive \
Hubic \
Backblaze B2 \
Yandex Disk \
SFTP \
FTP \
HTTP \
The local filesystem"
HOMEPAGE = "https://rclone.org/"

DEPENDS = "go-cross-${TARGET_ARCH}"
RDEPENDS_${PN} += "bash"

# Don't use gitpkgv here ...
inherit go

# ... because shitquake fails to eval nested variables like PV="git${PKGV}" later ...
# ... so keep PV updated manually (git rev-list --count <revision>)
PV = "1.38-git1575+d96e45b"
SRCREV = "d96e45ba5b060c472cb5f8a4769060f14eadf63a"

GO_IMPORT = "github.com/ncw/rclone"

SRC_URI = "git://${GO_IMPORT}.git;protocol=https;destsuffix=${BPN}-${PV}/src/${GO_IMPORT} \
           file://rclonefs \
           file://COPYING"

do_install_append() {
    rm -rf ${D}${libdir}
    install -m 755 ${WORKDIR}/rclonefs ${D}${bindir}
    ln -s rclone ${D}${bindir}/mount.rclone
}

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${WORKDIR}/COPYING;md5=bed161b82a1ecab65ff7ba3c3b960439"
