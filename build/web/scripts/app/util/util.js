// add/remove methods and events
$.widget( "ui.tabs", $.ui.tabs, {
    options: {
        add: null,
        remove: null,
        tabTemplate: "<li><a href='#{href}'><span>#{label}</span></a></li>"
    },

    add: function( url, label, newId,index ) {
        if ( index === undefined ) {
            index = this.anchors.length;
        }

        var doInsertAfter, panel,
        options = this.options,
        li = $( options.tabTemplate
            .replace( /#\{href\}/g, url )
            .replace( /#\{label\}/g, label ) ),
        id = newId ||(!url.indexOf( "#" ) ? url.replace( "#", "" ) : this._tabId( li ));

        li.addClass( "ui-state-default ui-corner-top" ).data( "ui-tabs-destroy", true );
        li.attr( "aria-controls", id );

        doInsertAfter = index >= this.tabs.length;

        // try to find an existing element before creating a new one
        panel = this.element.find( "#" + id );
        if ( !panel.length ) {
            panel = this._createPanel( id );
            if ( doInsertAfter ) {
                if ( index > 0 ) {
                    panel.insertAfter( this.panels.eq( -1 ) );
                } else {
                    panel.appendTo( this.element );
                }
            } else {
                panel.insertBefore( this.panels[ index ] );
            }
        }
        panel.addClass( "ui-tabs-panel ui-widget-content ui-corner-bottom" ).hide();

        if ( doInsertAfter ) {
            li.appendTo( this.tablist );
        } else {
            li.insertBefore( this.tabs[ index ] );
        }

        options.disabled = $.map( options.disabled, function( n ) {
            return n >= index ? ++n : n;
        });

        this.refresh();
        if ( this.tabs.length === 1 && options.active === false ) {
            this.option( "active", 0 );
        }

        this._trigger( "add", null,li);//, this._ui( this.anchors[ index ]))//, this.panels[ index ] ) );
        return this;
    },

    remove: function( index ) {
        index = this._getIndex( index );
        var options = this.options,
        tab = this.tabs.eq( index ).remove(),
        panel = this._getPanelForTab( tab ).remove();

        // If selected tab was removed focus tab to the right or
        // in case the last tab was removed the tab to the left.
        // We check for more than 2 tabs, because if there are only 2,
        // then when we remove this tab, there will only be one tab left
        // so we don't need to detect which tab to activate.
        //			if ( tab.hasClass( "ui-tabs-active" ) && this.anchors.length > 2 ) {
        //				this._activate( index + ( index + 1 < this.anchors.length ? 1 : -1 ) );
        //			}

        options.disabled = $.map(
            $.grep( options.disabled, function( n ) {
                return n !== index;
            }),
            function( n ) {
                return n >= index ? --n : n;
            });

        this.refresh();

        this._trigger( "remove", null, tab);//this._ui( tab.find( "a" )[ 0 ], panel[ 0 ] ) );
        return this;
    }
});

// length method
$.widget( "ui.tabs", $.ui.tabs, {
    length: function() {
        return this.anchors.length;
    }
});
// select method
$.widget( "ui.tabs", $.ui.tabs, {
    select: function( index ) {
        index = this._getIndex( index );
        if ( index === -1 ) {
            if ( this.options.collapsible && this.options.selected !== -1 ) {
                index = this.options.selected;
            } else {
                return;
            }
        }
        this.anchors.eq( index ).trigger( this.options.event + this.eventNamespace );
    }
});
(function($){
    $.MsgBox = {
        INFO:1,
        ALERT : 2,
        CONFIRM : 3,
        WARNING :4,
        show : function(config){
            var options = $.extend({
                title: "",
                msg: "",
                width:300,
                buttons: [],
                fn: null,
                icon:1
            },config);
            this.getIcon = function(){
                var icons = ["msg-info","msg-question","msg-warning","msg-error"];
                return icons[options.icon-1];
            }
            var ico = this.getIcon();
            var me = $('<div id="msg-box" ><div  style="width:15%;margin-top:7px;" class="msg-box-icon left '+ico+'">&nbsp</div><div class="left" style="padding-top:10px;font-size:14px;width:80%;">'+options.msg+'</div></div>').dialog({
                title:options.title,
                modal:true,
                width:options.width,
                buttons:{
                    'OK':function(){
                        $(this).dialog("close");
                    }
                        
                }
            });
            $("#msg-box").parent().removeClass("ui-corner-all");
            $("#msg-box").parent().css({
                padding:"0"
            });
        }
    }
    $.LoadMask ={
		msg: 'Loading, Please wait...',
		show: function(config) {
			$('#load-mask-body').removeClass('mask-hidden');
			$('#load-mask-body .load-msg').html(this.msg);		
		},
		hide: function(){
			$('#load-mask-body').addClass('mask-hidden');
		}
	};

})(jQuery);

