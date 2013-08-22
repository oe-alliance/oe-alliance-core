PRINC = "1"

pkg_postinst() {
#!/bin/sh

if [ ! -e /home/root/.profile ];then
	touch /home/root/.profile
fi
echo "export TERMINFO=/etc/terminfo" >> .profile
echo "export TERM=xterm" >> .profile
exit 0
}
