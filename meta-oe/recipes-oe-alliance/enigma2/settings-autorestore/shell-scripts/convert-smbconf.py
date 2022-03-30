#
# convert-smbconfig.py: Convert an smb.conf file into OpenPLi format
#

import sys
import os.path
import collections
import ConfigParser

# ini files processed by this command
SMBCONF = "/etc/samba/smb.conf"
SMBUSERCONF = "/etc/samba/smb-user.conf"

# location of the share config
SHAREPATH = '/etc/samba/shares'

#
# Our "Better" ConfigParser, which supports comments and a
# toplevel section that doesn't require an INI header
#


class BetterConfigParser(ConfigParser.ConfigParser):

	# class globals
	TOPLEVEL = "__toplevel__"             # Name of the dummy toplevel section

	# override the regex, we allow spaces in option names
	OPTCRE = ConfigParser.re.compile(
		r'(?P<option>[^:=][^:=]*)'          # very permissive!
		r'\s*(?P<vi>[:=])\s*'                 # any number of space/tab,
											  # followed by separator
											  # (either : or =), followed
											  # by any # space/tab
		r'(?P<value>.*)$'                     # everything up to eol
		)
	OPTCRE_NV = ConfigParser.re.compile(
		r'(?P<option>[^:=][^:=]*)'          # very permissive!
		r'\s*(?:'                             # any number of space/tab,
		r'(?P<vi>[:=])\s*'                    # optionally followed by
											  # separator (either : or
											  # =), followed by any #
											  # space/tab
		r'(?P<value>.*))?$'                   # everything up to eol
		)

	def optionxform(self, optionstr):
		"""Override optionxform to avoid case conversions"""
		return optionstr

	def add_comment(self, section, comment):
		"""Add a comment line"""
		self.set(section, '# %s' % (comment,), None)

	def add_section(self, section, items=None):
		"""Create a new section in the configuration.
		Raise DuplicateSectionError if a section by the specified name
		already exists. Raise ValueError if name is DEFAULT or any of it's
		case-insensitive variants.
		"""
		if section.lower() == "default":
			raise ValueError('Invalid section name: %s' % section)

		if section in self._sections:
			raise ConfigParser.DuplicateSectionError(section)

		self._sections[section] = collections.OrderedDict()
		if items is not None:
			for (key, value) in items:
				self._sections[section][self.optionxform(key)] = value

	def _read(self, fp, fpname):
		"""Parse a sectioned setup file, retaining comments"""

		cursect = None                        # None, or a dictionary
		optname = None                        # None, or a string
		lineno = 0                            # Integer
		e = None                              # None, or an exception

		# initialize the toplevel section and make it current
		self._sections = collections.OrderedDict()
		cursect = collections.OrderedDict()
		cursect['__name__'] = self.TOPLEVEL
		self._sections[self.TOPLEVEL] = cursect

		for line in fp.readlines():
			lineno = lineno + 1
			# we only process non-blank lines
			line = line.strip()
			if line:
				# comment line?
				if (line.split(None, 1)[0].lower() == 'rem' and line[0] in "rR") or line[0] in "#;":
					cursect['_comment_' + str(lineno)] = line
				else:
					# continuation line?
					if line[0].isspace() and cursect is not None and optname:
						if value:
							cursect[optname].append(value)
					# a section header or option header?
					else:
						# is it a section header?
						mo = self.SECTCRE.match(line)
						if mo:
							sectname = mo.group('header')
							if sectname in self._sections:
								cursect = self._sections[sectname]
							else:
								cursect = collections.OrderedDict()
								cursect['__name__'] = sectname
								self._sections[sectname] = cursect
							# So sections can't start with a continuation line
							optname = None
						# an option line?
						else:
							mo = self._optcre.match(line)
							if mo:
								optname, vi, optval = mo.group('option', 'vi', 'value')
								optname = self.optionxform(optname.rstrip())
								# This check is fine because the OPTCRE cannot
								# match if it would set optval to None
								if optval is not None:
									if vi in ('=', ':') and ';' in optval:
										# ';' is a comment delimiter only if it follows
										# a spacing character
										pos = optval.find(';')
										if pos != -1 and optval[pos - 1].isspace():
											optval = optval[:pos]
									optval = optval.strip()
									# allow empty values
									if optval == '""':
										optval = ''
									cursect[optname] = [optval]
								else:
									# valueless option handling
									cursect[optname] = optval
							else:
								# a non-fatal parsing error occurred.  set up the
								# exception but keep going. the exception will be
								# raised at the end of the file and will contain a
								# list of all bogus lines
								if not e:
									e = ConfigParser.ParsingError(fpname)
								e.append(lineno, repr(line))

		# if any parsing errors occurred, raise an exception
		if e:
			raise e

		# join the multi-line values collected while reading
		all_sections = [self._defaults]
		all_sections.extend(self._sections.values())
		for options in all_sections:
			for name, val in options.items():
				if isinstance(val, list):
					options[name] = '\n'.join(val)

	def write(self, filename):
		"""Write an .ini-format representation of the configuration state."""

		fp = open(filename, 'w')
		self.writefp(fp)
		fp.close()

	def writefp(self, fp):
		"""Write an .ini-format representation of the configuration state."""

		# write toplevel items first, if present
		if self.TOPLEVEL in self._sections:
			for (key, value) in self._sections[self.TOPLEVEL].items():
				self._write_item(fp, key, value)
			fp.write("\n")

		# write the defined sections
		for section in self._sections:
			if section != self.TOPLEVEL:
				fp.write("[%s]\n" % section)
				for (key, value) in self._sections[section].items():
					self._write_item(fp, key, value, 2)
				fp.write("\n")

	def _write_item(self, fp, key, value, indent=0):
		"""Write a single option or comment line"""
		if key != "__name__":
			if key.startswith('_comment_') and value is not None:
				fp.write("%s%s\n" % (" " * indent, value,))
			else:
				fp.write("%s%s = %s\n" % (" " * indent, key, str(value).replace('\n', '\n\t')))

# cli help information


def syntax():
	"""
	Show syntax and usage information
	"""
	print('''
Information:
	restore.smconf is a script that takes an smb.conf file as input, and tries to convert
	it into a format used by OpenPLi, which uses seperate files to configure end-user
	additions to the [global] section, and a seperate file per defined share.

	If will write updates found to the [global] and [root] sections to smb-user.conf,
	and will create share definition files for each of the other shares found.

Syntax:
	python restore-smbconf.py </some/path/smb.conf>
	''')
	sys.exit()

#
# main function
#


def main():
	"""
	CLI programme to convert smb.conf files into OpenPLi format
	"""

	# check the commandline args
	if len(sys.argv) < 2 or not os.path.exists(sys.argv[1]):
		syntax()

	# check the environment
	if not os.path.exists(SMBCONF):
		sys.exit('Required file %s not found' % SMBCONF)

	if not os.path.exists(SMBUSERCONF):
		sys.exit('Required file %s not found' % SMBUSERCONF)

	if not os.path.isdir(SHAREPATH):
		sys.exit('Required directory %s not found' % SHAREPATH)

	# read the smb.conf input passed
	inputfile = BetterConfigParser()
	inputfile.read(sys.argv[1])

	# read the system smb.conf
	smbconf = BetterConfigParser()
	smbconf.read(SMBCONF)

	# read the current smb-user.conf
	userconf = BetterConfigParser()
	userconf.read(SMBUSERCONF)

	# process the sections found in the smb.conf
	for section in inputfile.sections():
		# global samba config
		if section == 'global':
			# loop over the options and compare them
			for (key, value) in inputfile.items(section):
				# only for the keys we're interested in
				if not smbconf.has_option(section, key) or smbconf.get(section, key) != value:
					if key == 'security' and value == 'share':
						userconf.set(section, key, 'user')
						userconf.set(section, 'map to guest', 'Bad User')
					else:
						userconf.set(section, key, value)

			# write an updated smb-user.conf
			userconf.write(SMBUSERCONF)

		# root share or toplevel info?
		elif section.lower() == 'root' or section.lower() == smbconf.TOPLEVEL:
			# skip them
			continue

		# harddisk share
		elif section.lower() == 'harddisk':
			# will be created dynamically by mdev
			continue

		# other shares that may be defined
		else:
			path = inputfile.get(section, 'path')
			# only create shares for paths that exist
			if os.path.exists(path):
				share = BetterConfigParser()
				share.add_section(section, inputfile.items(section))
				share.write('%s/%s.conf' % (SHAREPATH, section.lower()))


# run me
if __name__ == '__main__':
	main()
