Ext.define("MyApp.controller.MainController", {
    extend:'Ext.app.Controller',

    views:['Main'],

    init:function ()
    {
        var me = this;

        me.count = 0;

        me.control({
            'numberfield':{
                change: me.onNumberfieldChange
            },
            'mainview':{
                afterrender: me.onAfterRender
            },
            'button[action=solve]':{
                click: me.solve
            },
            'button[action=resetSudoku]':{
                click: me.resetSudoku
            },
        });
    },

    onAfterRender: function(viewport) {
        var me = this,
            sudokuContainer = viewport.down('container[name=sudokuContainer]');

        me.view = viewport;

        for(var i=0; i<MyApp.Constants.SUDOKU_NINE; i++) {
            var rowContainer =  Ext.create('Ext.container.Container', {
                                    layout: {
                                        type: 'hbox',
                                        align: 'middle'
                                    },
                                    width: 300
                                });
            var numberfields = [];
            for(var j=0; j<MyApp.Constants.SUDOKU_NINE; j++) {
                numberfields.push({
                    xtype: 'numberfield',
                    hideTrigger: true,
                    width: 22,
                    maxLength: 1,
                    enforceMaxLength: true,
                    minValue: 1,
                    allowNegative: false,
                    margin: 0,
                    name: 'cell'+i+j
                });
            }
            rowContainer.add(numberfields);
            sudokuContainer.add(rowContainer);
        }
    },

    onNumberfieldChange: function(field, newValue, oldValue) {
        var me = this,
            row = parseInt(field.name.charAt(4)),
            column = parseInt(field.name.charAt(5));

        if(newValue != null) {
            me.count++;
            if(newValue == 0) {
                field.setValue(null);
            } else {
                //checks 3x3 group
                var iGroup = parseInt(row/3)*3,
                    jGroup = parseInt(column/3)*3;
                for(var i=iGroup; i<iGroup+3; i++) {
                    for(var j=jGroup; j<jGroup+3; j++) {
                        if((i != row || j != column) && newValue == me.view.down('numberfield[name=cell'+i+j+']').getValue()) {
                            Ext.Msg.alert('Attention', 'The same value cannot be twice within the same 3x3 cell grid');
                            field.setValue(null);
                            break;
                        }
                    }
                }

                //checks the same column
                for(var i=0; i<MyApp.Constants.SUDOKU_NINE; i++) {
                    if(i != row && newValue == me.view.down('numberfield[name=cell'+i+column+']').getValue()) {
                        Ext.Msg.alert('Attention', 'The same value cannot be twice in the same column');
                        field.setValue(null);
                        break;
                    }
                }

                //checks the same row
                for(var j=0; j<MyApp.Constants.SUDOKU_NINE; j++) {
                    if(j != column && newValue == me.view.down('numberfield[name=cell'+row+j+']').getValue()) {
                        Ext.Msg.alert('Attention', 'The same value cannot be twice in the same row');
                        field.setValue(null);
                        break;
                    }
                }
            }
        } else {
            me.count--;
        }
    },

    resetSudoku: function() {
        var me = this;
        for(var i=0; i<MyApp.Constants.SUDOKU_NINE; i++) {
            for(var j=0; j<MyApp.Constants.SUDOKU_NINE; j++) {
                me.view.down('numberfield[name=cell'+i+j+']').setValue(null);
                me.view.down('numberfield[name=cell'+i+j+']').setFieldStyle('background-color: white;');
            }
        }
    },

    solve: function() {
        var me = this;
        if(me.count<17) {
            Ext.Msg.alert('Attention', "There's no solution to 16-clue puzzles!");
        } else {
            var jsonArray = [];
            for(var i=0; i<MyApp.Constants.SUDOKU_NINE; i++) {
                for(var j=0; j<MyApp.Constants.SUDOKU_NINE; j++) {
                    jsonArray.push({
                        cellNumber : me.view.down('numberfield[name=cell'+i+j+']').getValue(),
                        empty: me.view.down('numberfield[name=cell'+i+j+']').getValue() == null
                    });
                }
            }

            Ext.Ajax.request({
                url: 'solve/getSolution',
                params: {
                    jsonArray: Ext.encode(jsonArray),
                },
                method: 'GET',
                headers: { 'Content-Type': 'application/json; charset=utf-8' },
                success: function(response, options){
                    var requestResponse = Ext.decode(response.responseText);
                    
                    if(Ext.isDefined(requestResponse.success)&&!requestResponse.success) {
                        Ext.Msg.show({
                            title:'Error',
                            msg: requestResponse.info,
                            buttons: Ext.Msg.OK,
                            icon: Ext.Msg.ERROR
                        });
                    } else {
                        var i = 0, j = 0;
                        Ext.Array.forEach(requestResponse,function(cell) {
                            if(cell.empty) {
                                me.view.down('numberfield[name=cell'+i+j+']').setValue(cell.cellNumber);
                                me.view.down('numberfield[name=cell'+i+j+']').setFieldStyle('background-color: greenyellow;');                              
                            } else {
                                me.view.down('numberfield[name=cell'+i+j+']').setFieldStyle('background-color: white;');
                            }
                            j++;
                            if(j >= MyApp.Constants.SUDOKU_NINE) {
                                j = 0;
                                i++;
                            }
                        });
                    }
                },
                failure: function(response, options) {
                    Ext.Msg.show({
                        title:'Error',
                        msg: 'Ups! Something went wrong',
                        buttons: Ext.Msg.OK,
                        icon: Ext.Msg.ERROR
                    });
                }
            });
        }
    }
});