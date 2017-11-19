/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.yan.anc.repository

import com.yan.anc.repository.Status.ERROR
import com.yan.anc.repository.Status.LOADING
import com.yan.anc.repository.Status.SUCCESS
import org.jetbrains.annotations.Nullable


/**
 * Created by Administrator on 2017/11/14.
 */
//a generic class that describes a data with a status
class Resource<T> private constructor(val status: Status, @param:Nullable @field:Nullable val data: T?, @param:Nullable @field:Nullable val message: String?, @param:Nullable @field:Nullable val isRefresh: Boolean?) {
    companion object {

        fun <T> success(data: T?, isRefresh: Boolean): Resource<T>? = Resource(SUCCESS, data, null, isRefresh)

        fun <T> error(msg: String?, @Nullable data: T?, isRefresh: Boolean): Resource<T>? = Resource(ERROR, data, msg, isRefresh)

        fun <T> loading(@Nullable data: T?, isRefresh: Boolean): Resource<T>? = Resource(LOADING, data, null, isRefresh)
    }
}