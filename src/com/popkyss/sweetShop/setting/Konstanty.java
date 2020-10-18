package com.popkyss.sweetShop.setting;

import java.awt.Color;

public abstract class Konstanty {
	public static interface IBarevne {
		Color getBarva();
	}

	public static interface IVycet {
		String getPopisek();

		String getName();

		int getOrdinal();
	}

	public enum TypSouboru implements IVycet {
		CSV {
			public String[] getMimeTypes() {
				return new String[] { "text/comma-separated-values", "text/plain", "application/vnd.ms-excel" };
			}
		},
		DOC {
			public String[] getMimeTypes() {
				return new String[] { "application/msword" };
			}
		},
		DOCX {
			public String[] getMimeTypes() {
				return new String[] { "application/vnd.openxmlformats-officedocument.wordprocessingml.document" };
			}
		},
		GIF {
			public String[] getMimeTypes() {
				return new String[] { "image/gif" };
			}
		},
		JPG {
			public String[] getMimeTypes() {
				return new String[] { "image/jpeg", "image/pjpeg" };
			}
		},
		PNG {
			public String[] getMimeTypes() {
				return new String[] { "image/png", "image/x-png" };
			}
		},
		PDF {
			public String[] getMimeTypes() {
				return new String[] { "application/pdf" };
			}
		},
		RTF {
			public String[] getMimeTypes() {
				return new String[] { "text/rtf", "application/rtf", "application/msword" };
			}
		},
		TXT {
			public String[] getMimeTypes() {
				return new String[] { "text/plain" };
			}
		},
		XLS {
			public String[] getMimeTypes() {
				return new String[] { "application/vnd.ms-excel" };
			}
		},
		XLSX {
			public String[] getMimeTypes() {
				return new String[] { "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" };
			}
		},
		XML {
			public String[] getMimeTypes() {
				return new String[] { "text/xml" };
			}
		},
		ZIP {
			public String[] getMimeTypes() {
				return new String[] { "application/zip", "application/x-zip-compressed" };
			}
		},
		JAR {
			public String[] getMimeTypes() {
				return new String[] { "application/octet-stream", "application/x-zip-compressed" };
			}
		},
		PPT {
			public String[] getMimeTypes() {
				return new String[] { "application/vnd.ms-powerpoint", "application/octet-stream" };
			}
		},
		PPTX {
			public String[] getMimeTypes() {
				return new String[] { "vnd.openxmlformats-officedocument.presentationml.presentation",
						"application/octet-stream" };
			}
		},
		OSTATNI {
			public String getPripona() {
				return "";
			}

			public String[] getMimeTypes() {
				return new String[0];
			}
		};

		public String getPripona() {
			return name().toLowerCase();
		}

		public String getPopisek() {
			return name();
		}

		public String getName() {
			return name();
		}

		public int getOrdinal() {
			return ordinal();
		}

		public abstract String[] getMimeTypes();
	}
}