
# duplicated split_and_strip_files, but this one removes all src from the dist package

python split_and_strip_files () {
	import commands, stat, errno

	dvar = d.getVar('PKGD', True)
	pn = d.getVar('PN', True)

	# We default to '.debug' style
	if d.getVar('PACKAGE_DEBUG_SPLIT_STYLE', True) == 'debug-file-directory':
		# Single debug-file-directory style debug info
		debugappend = ".debug"
		debugdir = ""
		debuglibdir = "/usr/lib/debug"
		debugsrcdir = "/usr/src/debug"
	else:
		# Original OE-core, a.k.a. ".debug", style debug info
		debugappend = ""
		debugdir = "/.debug"
		debuglibdir = ""
		debugsrcdir = "/usr/src/debug"

	os.chdir(dvar)

	# Return type (bits):
	# 0 - not elf
	# 1 - ELF
	# 2 - stripped
	# 4 - executable
	# 8 - shared library
	def isELF(path):
		type = 0
		pathprefix = "export PATH=%s; " % d.getVar('PATH', True)
		ret, result = commands.getstatusoutput("%sfile '%s'" % (pathprefix, path))

		if ret:
			bb.error("split_and_strip_files: 'file %s' failed" % path)
			return type

		# Not stripped
		if "ELF" in result:
			type |= 1
			if "not stripped" not in result:
				type |= 2
			if "executable" in result:
				type |= 4
			if "shared" in result:
				type |= 8
		return type


	#
	# First lets figure out all of the files we may have to process ... do this only once!
	#
	file_list = {}
	file_links = {}
	if (d.getVar('INHIBIT_PACKAGE_DEBUG_SPLIT', True) != '1') and \
	   (d.getVar('INHIBIT_PACKAGE_STRIP', True) != '1'):
		for root, dirs, files in os.walk(dvar):
			for f in files:
				file = os.path.join(root, f)
				# Only process files (and symlinks)... Skip files that are obviously debug files
				if not (debugappend != "" and file.endswith(debugappend)) and \
				   not (debugdir != "" and debugdir in os.path.dirname(file[len(dvar):])) and \
				   os.path.isfile(file):
					try:
						s = os.stat(file)
					except OSError, (err, strerror):
						if err != errno.ENOENT:
							raise
						# Skip broken symlinks
						continue
					# Is the item excutable?  Then we need to process it.
					if (s[stat.ST_MODE] & stat.S_IXUSR) or \
					   (s[stat.ST_MODE] & stat.S_IXGRP) or \
					   (s[stat.ST_MODE] & stat.S_IXOTH):
						# If it's a symlink, and points to an ELF file, we capture the readlink target
						if os.path.islink(file):
							target = os.readlink(file)
							if not os.path.isabs(target):
								ltarget = os.path.join(os.path.dirname(file), target)
							else:
								ltarget = target

							if isELF(ltarget):
								#bb.note("Sym: %s (%d)" % (ltarget, isELF(ltarget)))
								file_list[file] = "sym: " + target
							continue
						# It's a file (or hardlink), not a link
						# ...but is it ELF, and is it already stripped?
						elf_file = isELF(file)
						if elf_file & 1:
							# Check if it's a hard link to something else
							if s.st_nlink > 1:
								file_reference = "%d_%d" % (s.st_dev, s.st_ino)
								# Hard link to something else
								file_list[file] = "hard: " + file_reference
								continue

							file_list[file] = "ELF: %d" % elf_file


	#
	# Now lets go back over things and strip them
	#
	if (d.getVar('INHIBIT_PACKAGE_STRIP', True) != '1'):
		for file in file_list:
			if file_list[file].startswith("ELF: "):
				elf_file = int(file_list[file][5:])
				#bb.note("Strip %s" % file)
				runstrip(file, elf_file, d)


	if (d.getVar('INHIBIT_PACKAGE_STRIP', True) != '1'):
		for root, dirs, files in os.walk(dvar):
			for f in files:
				if not f.endswith(".ko"):
					continue
				runstrip(os.path.join(root, f), None, d)
	#
	# End of strip
	#
}
