package io.github.hobart.api;

import io.github.hobart.api.enums.ResultCodeEnum;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class ResultOps<T> {

    /** 状态码为成功 */
    public static final Predicate<Result<?>> CODE_SUCCESS = r -> ResultCodeEnum.isSuccess(r.getCode());

    /** 数据有值 */
    public static final Predicate<Result<?>> HAS_DATA = r -> r.getData() != null;

    /** 数据有值,并且包含元素 */
    public static final Predicate<Result<?>> HAS_ELEMENT = r -> r.getData() != null;

    /** 状态码为成功并且有值 */
    public static final Predicate<Result<?>> DATA_AVAILABLE = CODE_SUCCESS.and(HAS_DATA);

    private final Result<T> original;

    // ~ 初始化
    // ===================================================================================================

    ResultOps(Result<T> original) {
        this.original = original;
    }

    public static <T> ResultOps<T> of(Result<T> original) {
        return new ResultOps<>(Objects.requireNonNull(original));
    }

    // ~ 杂项方法
    // ===================================================================================================

    /**
     * 观察原始值
     * @return R
     */
    public Result<T> peek() {
        return original;
    }

    /**
     * 读取{@code code}的值
     * @return 返回code的值
     */
    public String getCode() {
        return original.getCode();
    }

    /**
     * 读取{@code data}的值
     * @return 返回 Optional 包装的data
     */
    public Optional<T> getData() {
        return Optional.ofNullable(original.getData());
    }

    /**
     * 有条件地读取{@code data}的值
     * @param predicate 断言函数
     * @return 返回 Optional 包装的data,如果断言失败返回empty
     */
    public Optional<T> getDataIf(Predicate<? super Result<?>> predicate) {
        return predicate.test(original) ? getData() : Optional.empty();
    }

    /**
     * 读取{@code msg}的值
     * @return 返回Optional包装的 msg
     */
    public Optional<String> getMsg() {
        return Optional.of(original.getMsg());
    }

    /**
     * 对{@code code}的值进行相等性测试
     * @param code 基准值
     * @return 返回ture表示相等
     */
    public boolean codeEquals(String code) {
        return Objects.equals(original.getCode(), code);
    }

    /**
     * 对{@code code}的值进行相等性测试
     * @param code 基准值
     * @return 返回ture表示不相等
     */
    public boolean codeNotEquals(String code) {
        return !codeEquals(code);
    }

    /**
     * 是否成功
     * @return 返回ture表示成功
     * @see ResultCodeEnum#SUCCESS
     */
    public boolean isSuccess() {
        return codeEquals(ResultCodeEnum.SUCCESS.getCode());
    }

    /**
     * 是否失败
     * @return 返回ture表示失败
     */
    public boolean notSuccess() {
        return !isSuccess();
    }

    // ~ 链式操作
    // ===================================================================================================

    /**
     * 断言{@code code}的值
     * @param expect 预期的值
     * @param func 用户函数,负责创建异常对象
     * @param <Ex> 异常类型
     * @return 返回实例，以便于继续进行链式操作
     * @throws Ex 断言失败时抛出
     */
    public <Ex extends Exception> ResultOps<T> assertCode(String expect, Function<? super Result<T>, ? extends Ex> func)
            throws Ex {
        if (codeNotEquals(expect)) {
            throw func.apply(original);
        }
        return this;
    }

    /**
     * 断言成功
     * @param func 用户函数,负责创建异常对象
     * @param <Ex> 异常类型
     * @return 返回实例，以便于继续进行链式操作
     * @throws Ex 断言失败时抛出
     */
    public <Ex extends Exception> ResultOps<T> assertSuccess(Function<? super Result<T>, ? extends Ex> func) throws Ex {
        return assertCode(ResultCodeEnum.SUCCESS.getCode(), func);
    }

    /**
     * 断言业务数据有值
     * @param func 用户函数,负责创建异常对象
     * @param <Ex> 异常类型
     * @return 返回实例，以便于继续进行链式操作
     * @throws Ex 断言失败时抛出
     */
    public <Ex extends Exception> ResultOps<T> assertDataNotNull(Function<? super Result<T>, ? extends Ex> func) throws Ex {
        if (Objects.isNull(original.getData())) {
            throw func.apply(original);
        }
        return this;
    }

    /**
     * 断言业务数据有值,并且包含元素
     * @param func 用户函数,负责创建异常对象
     * @param <Ex> 异常类型
     * @return 返回实例，以便于继续进行链式操作
     * @throws Ex 断言失败时抛出
     */
    public <Ex extends Exception> ResultOps<T> assertDataNotEmpty(Function<? super Result<T>, ? extends Ex> func) throws Ex {
        if (original.getData() == null) {
            throw func.apply(original);
        }
        return this;
    }

    /**
     * 对业务数据(data)转换
     * @param mapper 业务数据转换函数
     * @param <U> 数据类型
     * @return 返回新实例，以便于继续进行链式操作
     */
    public <U> ResultOps<U> map(Function<? super T, ? extends U> mapper) {
        Result<U> result = Results.result(mapper.apply(original.getData()), original.getCode(), original.getMsg());
        return of(result);
    }

    /**
     * 对业务数据(data)转换
     * @param predicate 断言函数
     * @param mapper 业务数据转换函数
     * @param <U> 数据类型
     * @return 返回新实例，以便于继续进行链式操作
     * @see ResultOps#CODE_SUCCESS
     * @see ResultOps#HAS_DATA
     * @see ResultOps#HAS_ELEMENT
     * @see ResultOps#DATA_AVAILABLE
     */
    public <U> ResultOps<U> mapIf(Predicate<? super Result<T>> predicate, Function<? super T, ? extends U> mapper) {
        Result<U> result = Results.result(mapper.apply(original.getData()), original.getCode(), original.getMsg());
        return of(result);
    }

    // ~ 数据消费
    // ===================================================================================================

    /**
     * 消费数据,注意此方法保证数据可用
     * @param consumer 消费函数
     */
    public void useData(Consumer<? super T> consumer) {
        consumer.accept(original.getData());
    }

    /**
     * 条件消费(错误代码匹配某个值)
     * @param consumer 消费函数
     * @param codes 错误代码集合,匹配任意一个则调用消费函数
     */
    public void useDataOnCode(Consumer<? super T> consumer, String... codes) {
        useDataIf(o -> Arrays.stream(codes).filter(c -> Objects.equals(original.getCode(),c)).findFirst().isPresent(), consumer);
    }

    /**
     * 条件消费(错误代码表示成功)
     * @param consumer 消费函数
     */
    public void useDataIfSuccess(Consumer<? super T> consumer) {
        useDataIf(CODE_SUCCESS, consumer);
    }

    /**
     * 条件消费
     * @param predicate 断言函数
     * @param consumer 消费函数,断言函数返回{@code true}时被调用
     * @see ResultOps#CODE_SUCCESS
     * @see ResultOps#HAS_DATA
     * @see ResultOps#HAS_ELEMENT
     * @see ResultOps#DATA_AVAILABLE
     */
    public void useDataIf(Predicate<? super Result<T>> predicate, Consumer<? super T> consumer) {
        if (predicate.test(original)) {
            consumer.accept(original.getData());
        }
    }
}
