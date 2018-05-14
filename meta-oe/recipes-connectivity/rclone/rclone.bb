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

# Don't use gitpkgv here ...
inherit go

# ... because shitquake fails to eval nested variables like PV="git${PKGV}" later ...
# ... so keep PV updated manually
PV = "1.37-git1532+d6eb625"
SRCREV="d6eb62581578e5b689ece20fc2901c361d4535ef"

GO_IMPORT = "github.com/ncw/rclone"

SRC_URI = "git://${GO_IMPORT}.git;protocol=https;destsuffix=${BPN}-${PV}/src/${GO_IMPORT} \
           file://rclonefs \
           file://COPYING"

do_configure() {
    cd ./src/${GO_IMPORT}
    export GOPATH=${WORKDIR}/${PN}-${PV}
    go get
}

do_compile() {
    cd ./src/${GO_IMPORT}
    export GOPATH=${WORKDIR}/${PN}-${PV}
    go build
}

do_install_append() {
    install -m 755 ${WORKDIR}/rclonefs ${D}${bindir}
    ln -s rclone ${D}${bindir}/mount.rclone
}

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${WORKDIR}/COPYING;md5=bed161b82a1ecab65ff7ba3c3b960439"
