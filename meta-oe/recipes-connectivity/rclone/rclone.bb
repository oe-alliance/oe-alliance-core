SUMMARY = "rsync for cloud storage"
DESCRIPTION = "Rclone is a command line program to sync files and directories to and from different cloud storage providers \
    Alibaba Cloud (Aliyun) Object Storage System (OSS) Amazon Drive Amazon S3 Backblaze B2 Box Ceph DigitalOcean Spaces \
    Dreamhost Dropbox FTP Google Cloud Storage Google Drive HTTP Hubic Jottacloud IBM COS S3 Koofr Memset Memstore Mega \
    Microsoft Azure Blob Storage Microsoft OneDrive Minio Nextcloud OVH OpenDrive OpenStack Swift Oracle Cloud Storage \
    ownCloud pCloud put.io QingStor Rackspace Cloud Files Scaleway SFTP Wasabi WebDAV Yandex Disk The local filesystem"
HOMEPAGE = "https://rclone.org/"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://COPYING;md5=bed161b82a1ecab65ff7ba3c3b960439"

DEPENDS += "go-cross-${TUNE_PKGARCH}"

RDEPENDS:${PN} += "bash"
RDEPENDS:${PN}-dev += "bash ${PYTHON_PN}-core"

inherit gitpkgv upx-compress

SRCREV = "${AUTOREV}"
PV = "V1.64+git${SRCPV}"
PKGV = "V1.64+git${GITPKGV}"


SRC_URI = "git://github.com/rclone/rclone;protocol=https;branch=master \
           file://rclonefs"

S = "${WORKDIR}/git"

do_compile[network] = "1"

do_compile() {
    ${TARGET_PREFIX}go build
}

do_install() {
    install -d ${D}${bindir}
    install -m 755 ${S}/rclone ${D}${bindir}
    install -m 755 ${WORKDIR}/rclonefs ${D}${bindir}
    ln -sf rclone ${D}${bindir}/mount.rclone
}
