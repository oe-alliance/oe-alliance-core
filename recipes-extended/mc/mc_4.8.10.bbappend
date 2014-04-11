pkg_postinst_${PN}() {
#!/bin/sh

if [ ! -e /home/root/.profile ];then
    touch /home/root/.profile
fi
echo "export TERMINFO=/etc/terminfo" >> .profile
echo "export TERM=xterm" >> .profile
exit 0
}
