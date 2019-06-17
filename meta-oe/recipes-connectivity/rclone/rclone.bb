SUMMARY = "rsync for cloud storage"
DESCRIPTION = "Rclone is a command line program to sync files and directories to and from different cloud storage providers \
    Alibaba Cloud (Aliyun) Object Storage System (OSS) Amazon Drive Amazon S3 Backblaze B2 Box Ceph DigitalOcean Spaces \
    Dreamhost Dropbox FTP Google Cloud Storage Google Drive HTTP Hubic Jottacloud IBM COS S3 Koofr Memset Memstore Mega \
    Microsoft Azure Blob Storage Microsoft OneDrive Minio Nextcloud OVH OpenDrive OpenStack Swift Oracle Cloud Storage \
    ownCloud pCloud put.io QingStor Rackspace Cloud Files Scaleway SFTP Wasabi WebDAV Yandex Disk The local filesystem"
HOMEPAGE = "https://rclone.org/"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${WORKDIR}/COPYING;md5=bed161b82a1ecab65ff7ba3c3b960439"

DEPENDS = "go-cross-${TUNE_PKGARCH}"
RDEPENDS_${PN} += "bash"

inherit gitpkgv go upx-compress

SRCREV = "${AUTOREV}"
PV = "1.47-DEV+git${SRCPV}"

SRC_URI = "git://${GO_IMPORT}.git;protocol=http \
           file://rclonefs \
           file://COPYING"

GO_DYNLINK_aarch64 = ""
GO_DYNLINK_arm = ""

GO_IMPORT = "github.com/ncw/rclone"

do_install_append() {
    rm -rf ${D}${libdir}
    rm ${D}${bindir}/test_all
    install -m 755 ${WORKDIR}/rclonefs ${D}${bindir}
    ln -s rclone ${D}${bindir}/mount.rclone
}
