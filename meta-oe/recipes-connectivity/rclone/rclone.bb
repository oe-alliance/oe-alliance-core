SUMMARY = "rsync for cloud storage"
DESCRIPTION = "Rclone is a command line program to sync files and directories to and from \
 \
Amazon Drive \
Amazon S3 / Dreamhost / Ceph / Minio / Wasabi \
Backblaze B2 \
Box \
Dropbox \
FTP \
Google Cloud Storage \
Google Drive \
HTTP \
Hubic \
Jottacloud \
Mega \
Microsoft Azure Blob Storage \
Microsoft OneDrive \
OpenDrive \
Openstack Swift / Rackspace cloud files / Memset Memstore / OVH / Oracle Cloud Storage \
pCloud \
QingStor \
SFTP \
Webdav / Owncloud / Nextcloud \
Yandex Disk \
The local filesystem"
HOMEPAGE = "https://rclone.org/"

DEPENDS = "go-cross-${TUNE_PKGARCH}"
RDEPENDS_${PN} += "bash"

# Don't use gitpkgv here ...
inherit go upx-compress

PR="r1"

# ... because shitquake fails to eval nested variables like PV="git${PKGV}" later ...
# ... so keep PV updated manually (git rev-list --count <revision>)
PV = "1.42-DEV-git2446+751bfd4"
SRCREV = "c2635e39cc462210500bbcbab43147096c8cdd35"

GO_IMPORT = "github.com/rclone/rclone"

SRC_URI = "git://${GO_IMPORT}.git;protocol=https;destsuffix=${BPN}-${PV}/src/${GO_IMPORT} \
           file://rclonefs \
           file://COPYING"

do_install_append() {
    rm -rf ${D}${libdir}
    rm ${D}${bindir}/test_all
    install -m 755 ${WORKDIR}/rclonefs ${D}${bindir}
    ln -s rclone ${D}${bindir}/mount.rclone
}

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${WORKDIR}/COPYING;md5=bed161b82a1ecab65ff7ba3c3b960439"
