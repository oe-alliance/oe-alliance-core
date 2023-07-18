SUMMARY = "crontab Clearmen"
MAINTAINER = "openATV Team"
SECTION = "base"
LICENSE = "proprietary"
inherit allarch
require conf/license/license-gplv2.inc

ALLOW_EMPTY:${PN} = "1"

pkg_postinst_ontarget:${PN}() {
#!/bin/sh                                                       
echo '*/5 * * * * sync && echo 3 > /proc/sys/vm/drop_caches' >> /etc/cron/crontabs/root
chown root:crontab /etc/cron/crontabs/root
chmod 600 /etc/cron/crontabs/root
}