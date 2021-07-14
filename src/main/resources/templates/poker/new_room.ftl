<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <link href="https://cdn.bootcdn.net/ajax/libs/spectre.css/0.5.9/spectre.min.css" rel="stylesheet">
    <link href="https://cdn.bootcdn.net/ajax/libs/spectre.css/0.5.9/spectre-icons.min.css" rel="stylesheet">
    <link href="https://cdn.bootcdn.net/ajax/libs/spectre.css/0.5.9/spectre-exp.min.css" rel="stylesheet">
    <!-- <link href="https://emoji-css.afeld.me/emoji.css" rel="stylesheet"> -->
    <link rel='stylesheet' href='https://unpkg.com/emoji.css/dist/emoji.min.css'>
    <title>Room</title>
    <style type="text/css">
        .card {
            box-shadow: 0 0.25rem 1rem rgb(48 188 66 / 30%);
            border-radius: 8px;
        }

        .ec_f_lg {
            font-size: 34px;
        }

        .ec_f_md {
            font-size: 24px;
        }
    </style>
</head>

<body>
<div class="container">
    <div class="columns bg-dark">
        <div class="column">
            <div class="hero hero-sm p-2">
                <div class="hero-body">
                    <h1>Simple Poker</h1>
                    <p>This is a hero example</p>
                </div>
            </div>
        </div>
    </div>

    <#if user.role == 'MASTER'>
        <div class="columns my-2">
            <div class="column col-7 col-mx-auto">
                <div class="columns">
                    <div class="column col-1 p-0">
                        <span class="ec ec-speaking-head ec_f_md"></span>
                    </div>
                    <div class="column col-5 p-0">
                        <input class="form-input is-success" type="text" id="input-example-1" placeholder="...">
                    </div>
                    <div class="column">
                        <button class="btn btn-info bg-gray">Send Message</button>
                    </div>
                </div>
            </div>
            <div class="column col-3 col-mr-auto text-right">
                <button class="btn btn-success">Flop</button>
                <button class="btn btn-secondary">Shuffle</button>
            </div>
        </div>
        <div class="divider"></div>
    <#else>
        <div class="columns my-2">
            <div class="column col-8 col-mx-auto col-sm-12">
                <button class="btn btn-action s-circle mr-2 my-2">0</button>
                <button class="btn btn-action s-circle mr-2 my-2">1</button>
                <button class="btn btn-action s-circle mr-2 my-2">2</button>
                <button class="btn btn-action s-circle mr-2 my-2">3</button>
                <button class="btn btn-action s-circle mr-2 my-2">5</button>
                <button class="btn btn-action s-circle mr-2 my-2">8</button>
                <button class="btn btn-action s-circle mr-2 my-2">13</button>
                <button class="btn btn-action s-circle mr-2 my-2">21</button>
                <button class="btn btn-link btn-action mr-2 my-2" style="border: 0;">
                    <span class="ec ec-sleepy ec_f_lg"></span>
                </button>
            </div>
            <div class="column col-2 col-mr-auto col-sm-12 text-right">
                <button class="btn btn-block">Cancel</button>
            </div>
        </div>
        <div class="divider"></div>
    </#if>
    <div class="columns my-2">
        <div class="column col-10 col-mx-auto col-sm-12 col-md-11 col-lg-11 my-2">
            <div class="columns">
                <#list room.pokers as poker>
                    <div class="column col-2 col-sm-6 col-md-4 col-lg-3">
                        <div class="card m-1">
                            <div class="card-header">
                                <div class="card-title h5">${poker.name?capitalize}</div>
<#--                                <div class="card-subtitle text-gray">${poker.role}</div>-->
                            </div>
                            <div class="card-body">
                                20
                            </div>
                            <div class="card-footer">
                                <button class="btn btn-link float-right">
                                    <span class="ec ec-alarm-clock ec_f_md"></span>
                                </button>
                            </div>
                        </div>
                    </div>
                </#list>
            </div>
        </div>
    </div>

    <div class="columns my-2">
        <div class="column">
            <div class="empty">
                <div class="empty-icon">
                    <span class="ec ec-busts-in-silhouette ec_f_lg"></span>
                </div>
                <p class="empty-title h5">Waiting for the pokers to enter the room.</p>
                <!-- <p class="empty-subtitle">Click the button to start a conversation.</p> -->
                <!-- <div class="empty-action">
                  <button class="btn btn-primary">Send a message</button>
                </div> -->
            </div>
        </div>
    </div>

</div>
</body>

</html>