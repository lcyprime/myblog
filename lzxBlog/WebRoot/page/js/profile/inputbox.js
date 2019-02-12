/**
 * 地区选择
 */
;
(function($) {
	var opts = {};
	var selectbox = {
		init: function(o) {
			var $o = $(o),
				_name = $o.attr('name'),
				_selectValue = $o.find('.opts > a.selected').attr('val') ? $o.find('.opts > a.selected').attr('val') : $o.find('.opts > a:first').attr('val'),
				_selectHtml = $o.find('.opts > a.selected').html() ? $o.find('.opts > a.selected').html() : $o.find('.opts > a:first').html();
			$o.addClass('sb');
			$o.append($('<div class="sb_icon arrow" />')).append($('<input type="hidden" name="' + _name + '" value="' + _selectValue + '">'));
			$('<div class="selected">' + _selectHtml + '</div>').insertBefore($o.children(':first'));
			$o.children('.opts').show();
			var optsWidth = $o.children('.opts').width();
			if(optsWidth == 0) {
				var optsChildWidth = [];
				var tempWidth = 0;
				$o.children('.opts').children('a').each(function() {
					optsChildWidth.push($(this).width());
				});
				for(var i = 0; i < optsChildWidth.length; i++) {
					if(optsChildWidth[i] > tempWidth)
						tempWidth = optsChildWidth[i]
				}
				optsWidth = tempWidth + 10;
			}
			$o.children('.opts').hide();
			$o.children('.opts').css('width', (optsWidth + 15));
//			var _width = (opts.width != 'auto') ? opts.width : $o.children('.opts').width();
			//修改框的大小
			$o.css({
//				'width': _width,
				'width': 150,
				'height': opts.height
			}).find('div.selected').css({
				'height': opts.height,
				'line-height': opts.height + 'px'
			});
			$o.find('.sb_icon').css({
				'top': ($o.height() - $o.find('.sb_icon').height()) / 2
			});
			$o.off('click').on('click', selectbox.toggle);
			$o.off('click', '.opts > a').on('click', '.opts > a', selectbox.select);
			$o.find('.opts').off('mouseenter mouseleave').on('mouseenter', selectbox.mouseenter).on('mouseleave', selectbox.mouseleave);
			$(document).off('click', selectbox.hide).on('click', selectbox.hide);
		},
		toggle: function(e) {
			e.stopPropagation();
			var $o = $(this);
			var $opts = $o.children('.opts');
			$o.find('a.selected').removeClass('none');
			selectbox.hide(null, $('.sb').not($o));
			$o.toggleClass('sb_active');
			$opts.css({
				'width': Math.max($opts.width(), $o.width()),
				'top': $o.height(),
				'left': -parseInt($o.css('border-left-width'))
			}).toggle($o.hasClass('sb_active'));
		},
		hide: function(e, objs) {
			var $o = objs ? objs : $('.sb');
			$o.removeClass('sb_active').children('.opts').hide().find('a.selected').removeClass('none');
		},
		select: function(e) {
			e.stopPropagation();
			var $o = $(this).parents('.sb:first');
			$o.trigger('click');
			$o.find('a.selected').removeClass('selected');
			$(this).addClass('selected');
			$o.find('div.selected').html($(this).html());
			$o.find('input').val($(this).attr('val'));
		},
		mouseenter: function(e) {
			e.stopPropagation();
			var $o = $(this);
			$o.find('a.selected').addClass('none');
		},
		mouseleave: function(e) {
			e.stopPropagation();
			var $o = $(this);
			$o.find('a.selected').removeClass('none');
		}
	};
	var checkbox = {
		init: function(o) {
			var $o = $(o),
				_name = $o.attr('name'),
				_value = $o.attr('val') ? $o.attr('val') : '',
				_isChecked = $o.hasClass('checked') ? true : false;
			$o.addClass('cb');
			$o.append($('<input type="hidden" name="' + _name + '" value="' + _value + '" />'));
			$o.click(checkbox.toggle);
			if($o.hasClass('all')) $o.click(checkbox.allOrNone);
			if(_isChecked) {
				$o.removeClass('checked');
				$o.click();
			}
		},
		toggle: function(e) {
			$(this).toggleClass('cb_active').children().attr('checked', ($(this).hasClass('cb_active') ? true : false));
		},
		allOrNone: function(e) {
			var cbAllName = $(this).attr('name');
			if(cbAllName.length > 3) {
				var cbOneName = cbAllName.substring(0, cbAllName.length - 3);
				var isChecked = $(this).hasClass('cb_active') ? true : false;
				if(isChecked) {
					$('.cb[name=' + cbOneName + ']').not($('.cb_active[name=' + cbOneName + ']')).click();
				} else {
					$('.cb_active[name=' + cbOneName + ']').click();
				}
			}
		}
	};
	var radiobox = {
			init: function(o) {
				var $o = $(o),
					_name = $o.attr('name'),
					_value = $o.attr('val') ? $o.attr('val') : '';
				_isChecked = $o.hasClass('checked') ? true : false;
				$o.addClass('rb');
				$o.append($('<input type="hidden" name="' + _name + '" value="' + _value + '" />'));
				$o.click(radiobox.toggle);
				if(_isChecked) {
					$o.removeClass('checked');
					$o.click();
				}
			},
			toggle: function() {
				var $o = $(this),
					_name = $o.attr('name');
				$('[name="' + _name + '"]').removeClass('rb_active').children().attr('checked', false);
				$o.addClass('rb_active').children().attr('checked', true);
			}
		},
		_init = function(o) {
			var type = $(o).attr('type');
			if(type == 'selectbox') {
				selectbox.init(o);
			} else if(type == 'checkbox') {
				checkbox.init(o);
			} else if(type == 'radiobox') {
				radiobox.init(o);
			}
		};
	$.fn.inputbox = function(options) {
		opts = $.extend({}, $.fn.inputbox.defaults, options);
		return this.each(function() {
			_init(this);
		});
	};
	$.fn.inputbox.defaults = {
		width: 'auto',
		height: 24
	};
})(jQuery);