/**
 * Really Simple Color Picker in jQuery
 *
 * Licensed under the MIT (MIT-LICENSE.txt) licenses.
 *
 * Copyright (c) 2008-2012
 * Lakshan Perera (www.laktek.com) & Daniel Lacy (daniellacy.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 * IN THE SOFTWARE.
 */

(function (jQuery) {
    /**
     * Create a couple private variables.
    **/
    var selectorOwner,
        activePalette,
        cItterate       = 0,
        templates       = {
            control : jQuery('<span class="colorPicker-picker"><input type="text" size="20" /></span>'),
            palette : jQuery('<div id="colorPicker_palette" class="colorPicker-palette" />'),
            swatch  : jQuery('<div class="colorPicker-swatch">&nbsp;</div>'),
            hexLabel: jQuery('<label for="colorPicker_hex">Hex</label>'),
            hexField: jQuery('<input type="text" id="colorPicker_hex" />')
        },
        transparent     = "transparent",
        lastColor;

    /**
     * Create our colorPicker function
    **/
    jQuery.fn.colorPicker = function (options) {

        return this.each(function () {
            // Setup time. Clone new elements from our templates, set some IDs, make shortcuts, jazzercise.
            var element      = jQuery(this),
                opts         = jQuery.extend({}, jQuery.fn.colorPicker.defaults, options),
                defaultColor = jQuery.fn.colorPicker.toHex(
                        (element.val().length > 0) ? element.val() : opts.pickerDefault
                    ),
                newControl   = templates.control.clone(),
                newPalette   = templates.palette.clone().attr('id', 'colorPicker_palette-' + cItterate),
                newHexLabel  = templates.hexLabel.clone(),
                newHexField  = templates.hexField.clone(),
                paletteId    = newPalette[0].id,
                swatch;


            /**
             * Build a color palette.
            **/
            jQuery.each(opts.colors, function (i) {
                swatch = templates.swatch.clone();

                if (opts.colors[i] === transparent) {
                    swatch.addClass(transparent).text('X');
                    jQuery.fn.colorPicker.bindPalette(newHexField, swatch, transparent);
                } else {
                    swatch.css("background-color", "#" + this);
                    jQuery.fn.colorPicker.bindPalette(newHexField, swatch);
                }
                swatch.appendTo(newPalette);
            });

            newHexLabel.attr('for', 'colorPicker_hex-' + cItterate);

            newHexField.attr({
                'id'    : 'colorPicker_hex-' + cItterate,
                'value' : defaultColor
            });

            newHexField.bind("keydown", function (event) {
                if (event.keyCode === 13) {
                    var hexColor = jQuery.fn.colorPicker.toHex(jQuery(this).val());
                    jQuery.fn.colorPicker.changeColor(hexColor ? hexColor : element.val());
                }
                if (event.keyCode === 27) {
                    jQuery.fn.colorPicker.hidePalette();
                }
            });

            newHexField.bind("keyup", function (event) {
              var hexColor = jQuery.fn.colorPicker.toHex(jQuery(event.target).val());
              jQuery.fn.colorPicker.previewColor(hexColor ? hexColor : element.val());
            });

            jQuery('<div class="colorPicker_hexWrap" />').append(newHexLabel).appendTo(newPalette);

            newPalette.find('.colorPicker_hexWrap').append(newHexField);

            jQuery("body").append(newPalette);

            newPalette.hide();


            /**
             * Build replacement interface for original color input.
            **/
            newControl.css("background-color", defaultColor);

            newControl.bind("click", function () {
                jQuery.fn.colorPicker.togglePalette(jQuery('#' + paletteId), jQuery(this));
            });

            if( options && options.onColorChange ) {
              newControl.data('onColorChange', options.onColorChange);
            } else {
              newControl.data('onColorChange', function() {} );
            }
            element.after(newControl);

            element.bind("change", function () {
                element.next(".colorPicker-picker").css(
                    "background-color", jQuery.fn.colorPicker.toHex(jQuery(this).val())
                );
            });

            // Hide the original input.
            element.val(defaultColor).hide();

            cItterate++;
        });
    };

    /**
     * Extend colorPicker with... all our functionality.
    **/
    jQuery.extend(true, jQuery.fn.colorPicker, {
        /**
         * Return a Hex color, convert an RGB value and return Hex, or return false.
         *
         * Inspired by http://code.google.com/p/jquery-color-utils
        **/
        toHex : function (color) {
            // If we have a standard or shorthand Hex color, return that value.
            if (color.match(/[0-9A-F]{6}|[0-9A-F]{3}$/i)) {
                return (color.charAt(0) === "#") ? color : ("#" + color);

            // Alternatively, check for RGB color, then convert and return it as Hex.
            } else if (color.match(/^rgb\(\s*(\d{1,3})\s*,\s*(\d{1,3})\s*,\s*(\d{1,3})\s*\)$/)) {
                var c = ([parseInt(RegExp.$1, 10), parseInt(RegExp.$2, 10), parseInt(RegExp.$3, 10)]),
                    pad = function (str) {
                        if (str.length < 2) {
                            for (var i = 0, len = 2 - str.length; i < len; i++) {
                                str = '0' + str;
                            }
                        }

                        return str;
                    };

                if (c.length === 3) {
                    var r = pad(c[0].toString(16)),
                        g = pad(c[1].toString(16)),
                        b = pad(c[2].toString(16));

                    return '#' + r + g + b;
                }

            // Otherwise we wont do anything.
            } else {
                return false;

            }
        },

        /**
         * Check whether user clicked on the selector or owner.
        **/
        checkMouse : function (event, paletteId) {
            var selector = activePalette,
                selectorParent = jQuery(event.target).parents("#" + selector.attr('id')).length;

            if (event.target === jQuery(selector)[0] || event.target === selectorOwner[0] || selectorParent > 0) {
                return;
            }

            jQuery.fn.colorPicker.hidePalette();
        },

        /**
         * Hide the color palette modal.
        **/
        hidePalette : function () {
            jQuery(document).unbind("mousedown", jQuery.fn.colorPicker.checkMouse);

            jQuery('.colorPicker-palette').hide();
        },

        /**
         * Show the color palette modal.
        **/
        showPalette : function (palette) {
            var hexColor = selectorOwner.prev("input").val();

            palette.css({
                top: selectorOwner.offset().top + (selectorOwner.outerHeight()),
                left: selectorOwner.offset().left
            });

            jQuery("#color_value").val(hexColor);

            palette.show();

            jQuery(document).bind("mousedown", jQuery.fn.colorPicker.checkMouse);
        },

        /**
         * Toggle visibility of the colorPicker palette.
        **/
        togglePalette : function (palette, origin) {
            // selectorOwner is the clicked .colorPicker-picker.
            if (origin) {
                selectorOwner = origin;
            }

            activePalette = palette;

            if (activePalette.is(':visible')) {
                jQuery.fn.colorPicker.hidePalette();

            } else {
                jQuery.fn.colorPicker.showPalette(palette);

            }
        },

        /**
         * Update the input with a newly selected color.
        **/
        changeColor : function (value) {
            selectorOwner.css("background-color", value);
            selectorOwner.prev("input").val(value.substring(1)).change();

            jQuery.fn.colorPicker.hidePalette();

            selectorOwner.data('onColorChange').call(selectorOwner, jQuery(selectorOwner).prev("input").attr("id"), value);
        },


        /**
         * Preview the input with a newly selected color.
        **/
        previewColor : function (value) {
            selectorOwner.css("background-color", value);
        },

        /**
         * Bind events to the color palette swatches.
        */
        bindPalette : function (paletteInput, element, color) {
            color = color ? color : jQuery.fn.colorPicker.toHex(element.css("background-color"));

            element.bind({
                click : function (ev) {
                    lastColor = color;

                    jQuery.fn.colorPicker.changeColor(color);
                },
                mouseover : function (ev) {
                    lastColor = paletteInput.val();

                    jQuery(this).css("border-color", "#598FEF");

                    paletteInput.val(color);

                    jQuery.fn.colorPicker.previewColor(color);
                },
                mouseout : function (ev) {
                    jQuery(this).css("border-color", "#000");

                    paletteInput.val(selectorOwner.css("background-color"));

                    paletteInput.val(lastColor);

                    jQuery.fn.colorPicker.previewColor(lastColor);
                }
            });
        }
    });

    /**
     * Default colorPicker options.
     *
     * These are publibly available for global modification using a setting such as:
     *
     * jQuery.fn.colorPicker.defaults.colors = ['151337', '111111']
     *
     * They can also be applied on a per-bound element basis like so:
     *
     * jQuery('#element1').colorPicker({pickerDefault: 'efefef', transparency: true});
     * jQuery('#element2').colorPicker({pickerDefault: '333333', colors: ['333333', '111111']});
     *
    **/
    jQuery.fn.colorPicker.defaults = {
        // colorPicker default selected color.
        pickerDefault : "FFFFFF",

        // Default color set.
        colors : [
              "990033", "ff3366", "cc0033", "ff0033", "ff9999", 
              "cc3366", "ffccff", "cc6699", "993366", "660033", 
              "cc3399", "ff99cc", "ff66cc", "ff99ff", "ff6699", 
              "cc0066", "ff0066", "ff3399", "ff0099", "ff33cc", 
              "ff00cc", "ff66ff", "ff33ff", "ff00ff", "cc0099", 
              "990066", "cc66cc", "cc33cc", "cc99ff", "cc66ff", 
              "cc33ff", "993399", "cc00cc", "cc00ff", "9900cc", 
              "990099", "cc99cc", "996699", "663366", "660099", 
              "9933cc", "660066", "9900ff", "9933ff", "9966cc", 
              "330033", "663399", "6633cc", "6600cc", "9966ff", 
              "330066", "6600ff", "6633ff", "ccccff", "9999ff", 
              "9999cc", "6666cc", "6666ff", "666699", "333366", 
              "333399", "330099", "3300cc", "3300ff", "3333ff", 
              "3333cc", "0066ff", "0033ff", "3366ff", "3366cc", 
              "000066", "000033", "0000ff", "000099", "0033cc", 
              "0000cc", "336699", "0066cc", "99ccff", "6699ff", 
              "003366", "6699cc", "006699", "3399cc", "0099cc", 
              "66ccff", "3399ff", "003399", "0099ff", "33ccff", 
              "00ccff", "99ffff", "66ffff", "33ffff", "00ffff", 
              "00cccc", "009999", "669999", "99cccc", "ccffff", 
              "33cccc", "66cccc", "339999", "336666", "006666", 
              "003333", "00ffcc", "33ffcc", "33cc99", "00cc99", 
              "66ffcc", "99ffcc", "00ff99", "339966", "006633", 
              "336633", "669966", "66cc66", "99ff99", "66ff66", 
              "339933", "99cc99", "66ff99", "33ff99", "33cc66", 
              "00cc66", "66cc99", "009966", "009933", "33ff66", 
              "00ff66", "ccffcc", "ccff99", "99ff66", "99ff33", 
              "00ff33", "33ff33", "00cc33", "33cc33", "66ff33", 
              "00ff00", "66cc33", "006600", "003300", "009900", 
              "33ff00", "66ff00", "99ff00", "66cc00", "00cc00", 
              "33cc00", "339900", "99cc66", "669933", "99cc33", 
              "336600", "669900", "99cc00", "ccff66", "ccff33", 
              "ccff00", "999900", "cccc00", "cccc33", "333300", 
              "666600", "999933", "cccc66", "666633", "999966", 
              "cccc99", "ffffcc", "ffff99", "ffff66", "ffff33", 
              "ffff00", "ffcc00", "ffcc66", "ffcc33", "cc9933", 
              "996600", "cc9900", "ff9900", "cc6600", "993300", 
              "cc6633", "663300", "ff9966", "ff6633", "ff9933", 
              "ff6600", "cc3300", "996633", "330000", "663333", 
              "996666", "cc9999", "993333", "cc6666", "ffcccc", 
              "ff3333", "cc3333", "ff6666", "660000", "990000", 
              "cc0000", "ff0000", "ff3300", "cc9966", "ffcc99", 
              "ffffff", "cccccc", "999999", "666666", "333333", 
              "000000", 
        ],

        // If we want to simply add more colors to the default set, use addColors.
        addColors : []
    };

})(jQuery);
