#!/usr/bin/env python3

import fcntl
import os
import select
import signal
import struct
import sys


EV_SYN = 0
EV_KEY = 1
SYN_REPORT = 0
KEY_ESC = 1
KEY_ENTER = 28
KEY_POWER = 116
KEY_STOP = 128
KEY_EXIT = 174
KEY_OK = 352
KEY_MAX = 0x2FF
BUS_VIRTUAL = 0x06

EVIOCGRAB = 0x40044590
UI_DEV_CREATE = 0x5501
UI_DEV_DESTROY = 0x5502
UI_SET_EVBIT = 0x40045564
UI_SET_KEYBIT = 0x40045565

INPUT_EVENT_FORMAT = "llHHi"
INPUT_EVENT_SIZE = struct.calcsize(INPUT_EVENT_FORMAT)
UINPUT_USER_DEV_FORMAT = "<80sHHHHI" + ("i" * 256)

running = True


def stop(_signal_number, _frame):
	global running
	running = False


def emit(device, seconds, microseconds, event_type, code, value):
	os.write(device, struct.pack(INPUT_EVENT_FORMAT, seconds, microseconds, event_type, code, value))


def terminate_browser():
	with os.scandir('/proc') as processes:
		for entry in processes:
			if not entry.name.isdigit():
				continue
			try:
				with open(os.path.join(entry.path, 'comm'), encoding='ascii') as process_name:
					if process_name.read().strip() == 'browser_shell':
						os.kill(int(entry.name), signal.SIGTERM)
			except (FileNotFoundError, ProcessLookupError, PermissionError):
				pass


def main():
	if len(sys.argv) != 2:
		return 2

	source = os.open(sys.argv[1], os.O_RDONLY | os.O_NONBLOCK)
	uinput = os.open("/dev/uinput", os.O_WRONLY | os.O_NONBLOCK)
	created = False
	grabbed = False
	try:
		fcntl.ioctl(uinput, UI_SET_EVBIT, EV_KEY)
		for code in range(KEY_MAX + 1):
			fcntl.ioctl(uinput, UI_SET_KEYBIT, code)
		configuration = struct.pack(
			UINPUT_USER_DEV_FORMAT,
			b"Vu+ Chromium IR keyboard",
			BUS_VIRTUAL,
			0x5655,
			0x8052,
			1,
			0,
			*([0] * 256)
		)
		os.write(uinput, configuration)
		fcntl.ioctl(uinput, UI_DEV_CREATE)
		created = True
		# Keep nxserver and Enigma2 from consuming the physical IR events
		# while Chromium is active. Closing source also releases the grab.
		fcntl.ioctl(source, EVIOCGRAB, 1)
		grabbed = True

		while running:
			ready, _, _ = select.select([source], [], [], 0.5)
			if not ready:
				continue
			data = os.read(source, INPUT_EVENT_SIZE * 32)
			for offset in range(0, len(data) - INPUT_EVENT_SIZE + 1, INPUT_EVENT_SIZE):
				seconds, microseconds, event_type, code, value = struct.unpack_from(INPUT_EVENT_FORMAT, data, offset)
				if event_type == EV_KEY:
					if code in (KEY_EXIT, KEY_POWER):
						if value == 1:
							terminate_browser()
						continue
					if code == KEY_OK:
						translated_code = KEY_ENTER
					elif code == KEY_STOP:
						translated_code = KEY_ESC
					else:
						translated_code = code
					emit(uinput, seconds, microseconds, EV_KEY, translated_code, value)
				elif event_type == EV_SYN and code == SYN_REPORT:
					emit(uinput, seconds, microseconds, EV_SYN, SYN_REPORT, value)
	finally:
		if grabbed:
			fcntl.ioctl(source, EVIOCGRAB, 0)
		if created:
			fcntl.ioctl(uinput, UI_DEV_DESTROY)
		os.close(uinput)
		os.close(source)

	return 0


if __name__ == "__main__":
	signal.signal(signal.SIGINT, stop)
	signal.signal(signal.SIGTERM, stop)
	sys.exit(main())
