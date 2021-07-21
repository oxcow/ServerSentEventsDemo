<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <link href="https://cdn.bootcdn.net/ajax/libs/spectre.css/0.5.9/spectre.min.css" rel="stylesheet">
    <link href="https://cdn.bootcdn.net/ajax/libs/spectre.css/0.5.9/spectre-icons.min.css" rel="stylesheet">
    <link href="https://cdn.bootcdn.net/ajax/libs/spectre.css/0.5.9/spectre-exp.min.css" rel="stylesheet">
    <title>Simple Poker</title>
</head>

<body>
<div class="container">
    <div class="columns bg-dark">
        <div class="column">
            <div class="hero hero-sm p-2">
                <div class="hero-body">
                    <h1>Simple Poker</h1>
                    <p>Create a new Room</p>
                </div>
            </div>
        </div>
    </div>
    <div class="columns my-2">
        <div class="column col-8 col-mx-auto my-2">

            <form class="form-horizontal" method="post" action="/demo/rooms/create">
                <div class="form-group">
                    <div class="col-3 col-sm-12 my-2">
                        <label class="form-label" for="input-name">Room Name</label>
                    </div>
                    <div class="col-9 col-sm-12 my-2">
                        <input class="form-input" type="text" id="input-name" name="name" placeholder="Name" required>
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-3 col-sm-12 my-2">
                        <label class="form-label" for="input-type">Room Type</label>
                    </div>
                    <div class="col-9 col-sm-12 my-2">
                        <label class="form-radio form-inline">
                            <input type="radio" id="input-type" name="type" value="PRIVATE" checked><i
                                    class="form-icon"></i> private
                        </label>
                        <label class="form-radio form-inline">
                            <input type="radio" name="type" value="PUBLIC"><i class="form-icon"></i> public
                        </label>
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-3 col-sm-12">
                        <label class="form-label" for="description">Description</label>
                    </div>
                    <div class="col-9 col-sm-12">
                        <textarea class="form-input" id="description" name="description" placeholder="Description"
                                  rows="3"></textarea>
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-9 col-sm-12 col-ml-auto">
                        <label class="form-switch">
                            <input type="checkbox" name="persist" value=1 checked><i class="form-icon"></i> Persist room
                            information.
                        </label>
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-3 col-sm-12">
                        <label class="form-label" for="owner_name">Owner Name</label>
                    </div>
                    <div class="col-9 col-sm-12">
                        <input type="text" class="form-input" id="owner_name" name="ownerName" placeholder="owner" required/>
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-9 col-sm-12 col-ml-auto">
                        <label class="form-switch float-right p-0">
                            <button class="btn btn-info">Create Room</button>
                        </label>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>