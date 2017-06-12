Ext.define('MyApp.view.Main', {
    extend:'Ext.container.Viewport',
    alias: 'widget.mainview',
    layout: 'fit',

    initComponent:function ()
    {
        var me = this;

        me.items = {items: [
            {
                xtype: 'panel',
                title: '<div style="text-align:center;">SUDOKU SOLVER by Fernando Alegría</div>',
                style: {
                    padding: '10px',
                    font: '1.2em'
                },
                width: 400
            },
            {
                xtype: 'panel',
                html: '<div>Introduce a Sudoku to solve:</div>',
                style: {
                    padding: '10 10 10 30'
                },
                border: false
            },
            {
                xtype: 'container',
                width: 600,
                height: 300,
                layout: {
                    type: 'hbox',
                    align: 'top'
                },
                items: [
                    {
                        xtype: 'container',
                        name: 'sudokuContainer',
                        width: 260,
                        height: 300,
                        style: {
                            padding: '10 10 10 30',
                        },
                        layout: {
                            type: 'vbox',
                            align: 'middle'
                        }
                    },
                    {
                        xtype: 'container',
                        width: 260,
                        height: 300,
                        layout: {
                            type: 'vbox',
                            align: 'middle'
                        },
                        items: [
                            {
                                xtype: 'button',
                                text: 'GET SOLUTION!',
                                margin: '5 0 0 0',
                                width: 100,
                                action: 'solve'
                            },
                            {
                                xtype: 'button',
                                text: 'UNDO',
                                margin: '5 0 0 0',
                                width: 100,
                                action: 'resetSudoku'
                            }
                        ]
                    }                    
                ]
            }
        ]};

        me.callParent();
    }
});