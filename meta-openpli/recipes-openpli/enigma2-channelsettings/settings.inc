INHIBIT_PACKAGE_STRIP = "1"

python do_remove_tarball() {
	import os
	try:
		os.remove(bb.data.expand("${DL_DIR}/${SETTINGS_FILENAME}.tar.gz.md5", d))
		os.remove(bb.data.expand("${DL_DIR}/${SETTINGS_FILENAME}.tar.gz", d))
	except:
		pass
}

addtask remove_tarball before do_fetch

python do_convert_bouquets() {
	import os
	import os.path
	s = bb.data.getVar('S', d, 1)
	for filename in ["tv", "radio"]:
		inpath = os.path.join(s, "var/tuxbox/config/enigma/userbouquets." + filename + ".epl")
		outpath = os.path.join(s, "var/tuxbox/config/enigma/bouquets." + filename)
		infile = open(inpath, "r")
		outfile = open(outpath, "w")
		lines = infile.readlines()
		for line in lines:
			if "#NAME" in line:
				outfile.write(line)
			elif "#SERVICE:" in line:
				parts = line.split('/')
				outfile.write("#SERVICE: 1:7:1:0:0:0:0:0:0:0:" + parts[-1])
		infile.close()
		outfile.close()
}

addtask convert_bouquets after do_compile before do_install

do_install() {
	install -d ${D}/etc/enigma2
	install -m 0644 ${S}/var/tuxbox/config/enigma/bouquets.* ${D}/etc/enigma2/
	install -m 0644 ${S}/var/tuxbox/config/enigma/userbouquet.* ${D}/etc/enigma2/
	install -m 0644 ${S}/var/tuxbox/config/enigma/services ${D}/etc/enigma2/lamedb
}

PACKAGE_ARCH = "all"
FILES_${PN} = "/"